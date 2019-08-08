package br.com.leonardoferreira.poc.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.RabbitListenerConfigUtils;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class RabbitListenerWithRetryAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware, SmartInitializingSingleton {

    private RabbitListenerEndpointRegistry endpointRegistry;

    private BeanFactory beanFactory;

    private final RabbitHandlerMethodFactoryAdapter messageHandlerMethodFactory = new RabbitHandlerMethodFactoryAdapter();

    private final RabbitListenerEndpointRegistrar registrar = new RabbitListenerEndpointRegistrar();

    private final AtomicInteger counter = new AtomicInteger();

    private final ConcurrentMap<Class<?>, TypeMetadata> typeCache = new ConcurrentHashMap<>();

    private int increment;

    private final RabbitListenerWithRetryPropertyMap rabbitListenerWithRetryPropertyMap;

    private final AmqpTemplate amqpTemplate;

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    public RabbitListenerWithRetryAnnotationBeanPostProcessor(final RabbitListenerWithRetryPropertyMap rabbitListenerWithRetryPropertyMap,
                                                              final AmqpTemplate amqpTemplate) {
        this.rabbitListenerWithRetryPropertyMap = rabbitListenerWithRetryPropertyMap;
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.registrar.setBeanFactory(this.beanFactory);

        if (this.beanFactory instanceof ListableBeanFactory) {
            Map<String, RabbitListenerConfigurer> instances = ((ListableBeanFactory) this.beanFactory).getBeansOfType(RabbitListenerConfigurer.class);
            for (RabbitListenerConfigurer configurer : instances.values()) {
                configurer.configureRabbitListeners(this.registrar);
            }
        }

        if (this.registrar.getEndpointRegistry() == null) {
            if (this.endpointRegistry == null) {
                Assert.state(this.beanFactory != null, "BeanFactory must be set to find endpoint registry by bean name");
                this.endpointRegistry = this.beanFactory.getBean(
                        RabbitListenerConfigUtils.RABBIT_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
                        RabbitListenerEndpointRegistry.class
                );
            }

            this.registrar.setEndpointRegistry(this.endpointRegistry);
        }

        this.registrar.setContainerFactoryBeanName("rabbitListenerContainerFactory");

        MessageHandlerMethodFactory handlerMethodFactory = this.registrar.getMessageHandlerMethodFactory();
        if (handlerMethodFactory != null) {
            this.messageHandlerMethodFactory.setMessageHandlerMethodFactory(handlerMethodFactory);
        }

        this.registrar.afterPropertiesSet();

        this.typeCache.clear();
    }


    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        final TypeMetadata metadata = this.typeCache.computeIfAbsent(targetClass, this::buildMetadata);

        for (ListenerMethod lm : metadata.getListenerMethods()) {
            processAmqpListener(lm.getAnnotation(), lm.getMethod(), bean);
        }

        return bean;
    }

    private TypeMetadata buildMetadata(final Class<?> targetClass) {
        final List<ListenerMethod> methods = new ArrayList<>();
        ReflectionUtils.doWithMethods(targetClass, method -> {
            RabbitListenerWithRetry listenerAnnotation = AnnotationUtils.findAnnotation(method, RabbitListenerWithRetry.class);
            if (listenerAnnotation != null) {
                methods.add(new ListenerMethod(method, listenerAnnotation));
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);

        if (methods.isEmpty()) {
            return TypeMetadata.EMPTY;
        }

        return new TypeMetadata(
                methods.toArray(new ListenerMethod[0])
        );
    }

    private void processAmqpListener(final RabbitListenerWithRetry rabbitListener,
                                     final Method method,
                                     final Object bean) {
        Method methodToUse = checkProxy(method, bean);
        MethodRabbitListenerEndpoint endpoint = new MethodRabbitListenerEndpoint();
        endpoint.setMethod(methodToUse);
        processListener(endpoint, rabbitListener, bean);
    }

    private Method checkProxy(final Method methodArg, final Object bean) {
        Method method = methodArg;
        if (AopUtils.isJdkDynamicProxy(bean)) {
            try {
                method = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                Class<?>[] proxiedInterfaces = ((Advised) bean).getProxiedInterfaces();
                for (Class<?> proxy : proxiedInterfaces) {
                    try {
                        method = proxy.getMethod(method.getName(), method.getParameterTypes());
                        break;
                    } catch (NoSuchMethodException ignored) {
                    }
                }
            } catch (SecurityException ex) {
                ReflectionUtils.handleReflectionException(ex);
            } catch (NoSuchMethodException ex) {
                throw new IllegalStateException(String.format(
                        "@RabbitListener method '%s' found on bean target class '%s', " +
                                "but not found in any interface(s) for a bean JDK proxy. Either " +
                                "pull the method up to an interface or switch to subclass (CGLIB) " +
                                "proxies by setting proxy-target-class/proxyTargetClass " +
                                "attribute to 'true'", method.getName(), method.getDeclaringClass().getSimpleName()), ex);
            }
        }
        return method;
    }

    private void processListener(final MethodRabbitListenerEndpoint endpoint,
                                 final RabbitListenerWithRetry rabbitListener,
                                 final Object bean) {
        final RabbitListenerWithRetryProperty rabbitListenerWithRetryProperty = rabbitListenerWithRetryPropertyMap.findByName(rabbitListener.event());

        endpoint.setBean(bean);
        endpoint.setMessageHandlerMethodFactory(this.messageHandlerMethodFactory);
        endpoint.setId(getEndpointId());
        endpoint.setQueueNames(rabbitListenerWithRetryProperty.getQueue());
        endpoint.setConcurrency(String.valueOf(rabbitListenerWithRetryProperty.getConcurrency()));
        endpoint.setBeanFactory(this.beanFactory);
        endpoint.setErrorHandler(new RabbitListenerWithRetryErrorHandler(amqpTemplate, rabbitListener, rabbitListenerWithRetryProperty));
        endpoint.setReturnExceptions(false);
        endpoint.setExclusive(false);

        registerBeansForDeclaration(rabbitListenerWithRetryProperty);
        this.registrar.registerEndpoint(endpoint, null);
    }

    private String getEndpointId() {
        return "org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#" + this.counter.getAndIncrement();
    }

    private void registerBeansForDeclaration(final RabbitListenerWithRetryProperty property) {
        Queue queue = declareQueue(property);
        Exchange exchange = declareExchange(property);
        bindQueueAndExchange(queue, exchange, property);

        if (property.isCreateDlq()) {
            Queue dlq = declareDlq(property);
            bindDlqAndExchange(dlq, exchange, property);
        }
    }

    private Queue declareQueue(final RabbitListenerWithRetryProperty property) {
        final Map<String, Object> arguments = new HashMap<>();

        if (property.isCreateDlq()) {
            arguments.put("x-dead-letter-exchange", property.getExchange());
            arguments.put("x-dead-letter-routing-key", property.getDlq());
        }

        final Queue queue = QueueBuilder.durable(property.getQueue())
                .withArguments(arguments)
                .build();

        registerSingleton(property.getQueue(), queue);

        return queue;
    }

    private Queue declareDlq(final RabbitListenerWithRetryProperty property) {
        Queue queue = QueueBuilder.durable(property.getDlq())
                .build();

        registerSingleton(property.getDlq(), queue);

        return queue;
    }

    private Exchange declareExchange(final RabbitListenerWithRetryProperty property) {
        Exchange exchange = ExchangeBuilder.topicExchange(property.getExchange())
                .durable(true)
                .build();

        registerSingleton(property.getExchange(), exchange);

        return exchange;
    }

    private void bindQueueAndExchange(final Queue queue,
                                      final Exchange exchange,
                                      final RabbitListenerWithRetryProperty property) {
        final Binding binding = BindingBuilder
                .bind(queue).to(exchange)
                .with(property.getRoutingKey())
                .noargs();
        registerSingleton(property.getExchange() + "." + property.getQueue(), binding);
    }

    private void bindDlqAndExchange(final Queue dlq,
                                    final Exchange exchange,
                                    final RabbitListenerWithRetryProperty property) {
        final Binding binding = BindingBuilder
                .bind(dlq).to(exchange)
                .with(property.getDlq())
                .noargs();

        registerSingleton(property.getExchange() + "." + property.getDlq(), binding);
    }

    private class RabbitHandlerMethodFactoryAdapter implements MessageHandlerMethodFactory {

        private MessageHandlerMethodFactory messageHandlerMethodFactory;

        private void setMessageHandlerMethodFactory(MessageHandlerMethodFactory rabbitHandlerMethodFactory1) {
            this.messageHandlerMethodFactory = rabbitHandlerMethodFactory1;
        }

        @Override
        public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
            return getMessageHandlerMethodFactory().createInvocableHandlerMethod(bean, method);
        }

        private MessageHandlerMethodFactory getMessageHandlerMethodFactory() {
            if (this.messageHandlerMethodFactory == null) {
                this.messageHandlerMethodFactory = createDefaultMessageHandlerMethodFactory();
            }
            return this.messageHandlerMethodFactory;
        }

        private MessageHandlerMethodFactory createDefaultMessageHandlerMethodFactory() {
            DefaultMessageHandlerMethodFactory defaultFactory = new DefaultMessageHandlerMethodFactory();
            defaultFactory.setBeanFactory(RabbitListenerWithRetryAnnotationBeanPostProcessor.this.beanFactory);
            defaultFactory.afterPropertiesSet();
            return defaultFactory;
        }

    }

    private void registerSingleton(final String beanName, final Object singletonObject) {
        ((ConfigurableBeanFactory) this.beanFactory)
                .registerSingleton(beanName + ++this.increment, singletonObject);
    }

}

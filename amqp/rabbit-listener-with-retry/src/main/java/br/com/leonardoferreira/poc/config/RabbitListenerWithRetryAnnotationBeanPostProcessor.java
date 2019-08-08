package br.com.leonardoferreira.poc.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.amqp.core.AmqpTemplate;
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
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

public class RabbitListenerWithRetryAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware, SmartInitializingSingleton {

    private RabbitListenerEndpointRegistry endpointRegistry;

    private BeanFactory beanFactory;

    private final RabbitListenerWithRetryEventIdGenerator eventIdGenerator;

    private final RabbitListenerEndpointRegistrar registrar;

    private final ConcurrentMap<Class<?>, RabbitListenerWithRetryTypeMetadata> typeCache;

    private final RabbitListenerWithRetryPropertyMap rabbitListenerWithRetryPropertyMap;

    private final AmqpTemplate amqpTemplate;

    private final RabbitListenerWithRetryQueueRegister rabbitListenerWithRetryQueueRegister;


    public RabbitListenerWithRetryAnnotationBeanPostProcessor(final RabbitListenerWithRetryPropertyMap rabbitListenerWithRetryPropertyMap,
                                                              final AmqpTemplate amqpTemplate,
                                                              final RabbitListenerWithRetryQueueRegister rabbitListenerWithRetryQueueRegister) {
        this.rabbitListenerWithRetryPropertyMap = rabbitListenerWithRetryPropertyMap;
        this.amqpTemplate = amqpTemplate;
        this.rabbitListenerWithRetryQueueRegister = rabbitListenerWithRetryQueueRegister;
        this.eventIdGenerator = new RabbitListenerWithRetryEventIdGenerator();
        this.registrar = new RabbitListenerEndpointRegistrar();
        this.typeCache = new ConcurrentHashMap<>();
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) {
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
                this.endpointRegistry = this.beanFactory.getBean(
                        RabbitListenerConfigUtils.RABBIT_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
                        RabbitListenerEndpointRegistry.class
                );
            }

            this.registrar.setEndpointRegistry(this.endpointRegistry);
        }

        this.registrar.setContainerFactoryBeanName("rabbitListenerContainerFactory");
        this.registrar.afterPropertiesSet();
        this.typeCache.clear();
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        final RabbitListenerWithRetryTypeMetadata metadata = this.typeCache.computeIfAbsent(
                AopUtils.getTargetClass(bean),
                this::buildMetadata
        );

        for (RabbiListenerWithRetryMethod lm : metadata.getListenerMethods()) {
            processListener(
                    lm.getAnnotation(),
                    rabbitListenerWithRetryPropertyMap.findByName(lm.getAnnotation().event()),
                    checkProxy(lm.getMethod(), bean),
                    bean
            );
        }

        return bean;
    }

    private RabbitListenerWithRetryTypeMetadata buildMetadata(final Class<?> targetClass) {
        final List<RabbiListenerWithRetryMethod> methods = new ArrayList<>();
        ReflectionUtils.doWithMethods(targetClass, method -> {
            RabbitListenerWithRetry listenerAnnotation = AnnotationUtils.findAnnotation(method, RabbitListenerWithRetry.class);
            if (listenerAnnotation != null) {
                methods.add(new RabbiListenerWithRetryMethod(method, listenerAnnotation));
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);

        if (methods.isEmpty()) {
            return RabbitListenerWithRetryTypeMetadata.EMPTY;
        }

        return new RabbitListenerWithRetryTypeMetadata(methods);
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
                throw new IllegalStateException(ex);
            }
        }
        return method;
    }

    private void processListener(final RabbitListenerWithRetry rabbitListener,
                                 final RabbitListenerWithRetryProperty property,
                                 final Method method,
                                 final Object bean) {
        final MethodRabbitListenerEndpoint endpoint = new MethodRabbitListenerEndpoint();

        endpoint.setMethod(method);
        endpoint.setBean(bean);
        endpoint.setMessageHandlerMethodFactory(new RabbitListenerWithRetryHandlerMethodFactoryAdapter(beanFactory));
        endpoint.setId(eventIdGenerator.next());
        endpoint.setQueueNames(property.getQueue());
        endpoint.setConcurrency(String.valueOf(property.getConcurrency()));
        endpoint.setBeanFactory(this.beanFactory);
        endpoint.setErrorHandler(new RabbitListenerWithRetryErrorHandler(amqpTemplate, rabbitListener, property));
        endpoint.setReturnExceptions(false);
        endpoint.setExclusive(false);

        rabbitListenerWithRetryQueueRegister.registerQueuesByProperty(property);
        this.registrar.registerEndpoint(endpoint, null);
    }

}

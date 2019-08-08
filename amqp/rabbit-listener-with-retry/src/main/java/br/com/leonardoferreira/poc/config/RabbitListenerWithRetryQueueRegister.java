package br.com.leonardoferreira.poc.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

class RabbitListenerWithRetryQueueRegister implements BeanFactoryAware {

    private BeanFactory beanFactory;

    private int increment;

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    void registerQueuesByProperty(final RabbitListenerWithRetryProperty property) {
        Queue queue = declareQueue(property);
        Exchange exchange = declareExchange(property);
        bindQueueAndExchange(queue, exchange, property);

        if (property.isCreateRetry()) {
            Queue retry = declareRetry(property);
            bindRetryWithExchange(retry, exchange, property);
        }

        if (property.isCreateDlq()) {
            Queue dlq = declareDlq(property);
            bindDlqAndExchange(dlq, exchange, property);
        }
    }

    private void registerSingleton(final String beanName, final Object singletonObject) {
        ((ConfigurableBeanFactory) this.beanFactory)
                .registerSingleton(beanName + ++this.increment, singletonObject);
    }

    private void bindRetryWithExchange(final Queue retry,
                                       final Exchange exchange,
                                       final RabbitListenerWithRetryProperty property) {
        final Binding binding = BindingBuilder
                .bind(retry).to(exchange)
                .with(property.getRetry())
                .noargs();
        registerSingleton(property.getExchange() + "." + property.getRetry(), binding);
    }

    private Queue declareRetry(final RabbitListenerWithRetryProperty property) {
        final Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", property.getExchange());
        arguments.put("x-dead-letter-routing-key", property.getRoutingKey());

        final Queue queue = QueueBuilder.durable(property.getRetry())
                .withArguments(arguments)
                .build();

        registerSingleton(property.getRetry(), queue);

        return queue;
    }

    private Queue declareQueue(final RabbitListenerWithRetryProperty property) {
        final Queue queue = QueueBuilder.durable(property.getQueue())
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
}

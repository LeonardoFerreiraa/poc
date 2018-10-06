package br.com.leonardoferreira.pocamqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirstQueueConfig {

    public static final String QUEUE_NAME = "first_queue";

    public static final String CONTAINER_FACTORY = "firstQueueContainerFactory";

    private static final String EXCHANGE_NAME = "first_queue_exchange";

    private static final String DLQ_NAME = "first_queue_dlq";

    private static final String QUEUE = "firstQueue";

    private static final String EXCHANGE = "firstQueueExchange";

    private static final String BINDING = "firstQueueBinding";

    private static final String DLQ = "firstQueueDlq";

    private static final String BINDING_DLQ = "firstQueueBingingDlq";

    @Bean(QUEUE)
    public Queue queue() {
        return QueueBuilder
                .durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", DLQ_NAME)
                .build();
    }

    @Bean(EXCHANGE)
    public Exchange exchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME)
                .build();
    }

    @Bean(BINDING)
    public Binding binding(@Qualifier(QUEUE) Queue queue, @Qualifier(EXCHANGE) TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("");
    }

    @Bean(DLQ)
    public Queue dlq() {
        return QueueBuilder
                .durable(DLQ_NAME)
                .build();
    }

    @Bean(BINDING_DLQ)
    public Binding bindingDlq(@Qualifier(DLQ) Queue queue, @Qualifier(EXCHANGE) TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(DLQ_NAME);
    }

    @Bean(CONTAINER_FACTORY)
    public SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(RetryInterceptorBuilder
                .stateless()
                .maxAttempts(3)
                .backOffOptions(3_000, 2, 9_000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build());
        factory.setConcurrentConsumers(5);
        return factory;
    }

}

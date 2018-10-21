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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class FirstQueueConfig {

    public static final String QUEUE_NAME = "first_queue";

    private static final String EXCHANGE_NAME = "first_queue_exchange";

    private static final String DLQ_NAME = "first_queue_dlq";

    @Bean
    public Queue firstQueue() {
        return QueueBuilder
                .durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", DLQ_NAME)
                .build();
    }

    @Bean
    public Exchange firstQueueExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME)
                .build();
    }

    @Bean
    public Binding firstQueueBinding(Queue firstQueue, TopicExchange firstQueueExchange) {
        return BindingBuilder
                .bind(firstQueue)
                .to(firstQueueExchange)
                .with("");
    }

    @Bean
    public Queue firstQueueDlq() {
        return QueueBuilder
                .durable(DLQ_NAME)
                .build();
    }

    @Bean
    public Binding firstQueueBindingDlq(Queue firstQueueDlq, TopicExchange firstQueueExcahnge) {
        return BindingBuilder
                .bind(firstQueueDlq)
                .to(firstQueueExcahnge)
                .with(DLQ_NAME);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory firstQueueContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(retryAdvice());
        factory.setConcurrentConsumers(5);
        return factory;
    }

    private RetryOperationsInterceptor retryAdvice() {
        return RetryInterceptorBuilder
                .stateless()
                .maxAttempts(3)
                .backOffOptions(3_000, 2, 9_000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }

}

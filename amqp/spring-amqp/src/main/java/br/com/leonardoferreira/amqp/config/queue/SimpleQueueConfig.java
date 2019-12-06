package br.com.leonardoferreira.amqp.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleQueueConfig {

    public static final String QUEUE_NAME = "simple.queue";

    public static final String EXCHANGE_NAME = "simple.exchange";

    public static final String QUEUE_ROUTING_KEY = QUEUE_NAME;

    private static final String DLQ_NAME = "simple.queue.dlq";

    private static final String DLQ_ROUTING_KEY = DLQ_NAME;

    @Bean("simpleQueue")
    public Queue simpleQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .build();
    }

    @Bean("simpleQueueExchange")
    public Exchange simpleQueueExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME)
                .build();
    }

    @Bean("simpleQueueDlq")
    public Queue simpleQueueDlq() {
        return QueueBuilder.durable(DLQ_NAME)
                .build();
    }

    @Bean("simpleQueueExchangeBinding")
    public Binding simpleQueueExchangeBinding(
            @Qualifier("simpleQueue") final Queue queue,
            @Qualifier("simpleQueueExchange") final Exchange exchange
    ) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(QUEUE_ROUTING_KEY)
                .noargs();
    }

    @Bean("simpleQueueDlqExchangeBinding")
    public Binding simpleQueueDlqExchangeBinding(
            @Qualifier("simpleQueueDlq") final Queue queue,
            @Qualifier("simpleQueueExchange") final Exchange exchange
    ) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(DLQ_ROUTING_KEY)
                .noargs();
    }


}

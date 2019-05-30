package br.com.leonardoferreira.amqp.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleListener {

    @RabbitListener(
            concurrency = "10",
            bindings = {
                    @QueueBinding(
                            key = "",
                            exchange = @Exchange(
                                    name = "simple.exchange",
                                    type = ExchangeTypes.TOPIC
                            ),
                            value = @Queue(
                                    name = "simple.queue",
                                    durable = "true",
                                    arguments = {
                                            @Argument(
                                                    name = "x-dead-letter-exchange",
                                                    value = "simple.exchange"
                                            ),
                                            @Argument(
                                                    name = "x-dead-letter-routing-key",
                                                    value = "simple.queue.dlq"
                                            )
                                    }
                            )
                    )
            }
    )
    public void receiveMessage(String simpleMessage) {
        throw new RuntimeException();
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(QueueBuilder.durable("simple.queue.dlq").build())
                .to(ExchangeBuilder.topicExchange("simple.topic").build())
                .with("simple.queue.dlq")
                .noargs();
    }

}

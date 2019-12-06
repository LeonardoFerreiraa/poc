package br.com.leonardoferreira.amqp.scheduler;

import br.com.leonardoferreira.amqp.config.queue.SimpleQueueConfig;
import br.com.leonardoferreira.amqp.domain.SimpleMessage;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimpleScheduler {

    private final RabbitTemplate rabbitTemplate;

    public SimpleScheduler(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void produce() {
        final SimpleMessage simpleMessage = SimpleMessage.builder()
                .message(UUID.randomUUID().toString())
                .build();

        rabbitTemplate.convertAndSend(SimpleQueueConfig.EXCHANGE_NAME, SimpleQueueConfig.QUEUE_ROUTING_KEY, simpleMessage);
    }

}

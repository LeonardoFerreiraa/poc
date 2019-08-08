package br.com.leonardoferreira.poc.scheduler;

import br.com.leonardoferreira.poc.domain.SimpleMessage;
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
    public void scheduleASimpleMessage() {
        final SimpleMessage message = new SimpleMessage(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("simple.exchange", "", message);
    }

}

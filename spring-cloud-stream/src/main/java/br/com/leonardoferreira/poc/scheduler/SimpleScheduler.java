package br.com.leonardoferreira.poc.scheduler;

import br.com.leonardoferreira.poc.channel.SimpleKafkaChannel;
import br.com.leonardoferreira.poc.domain.SimpleMessage;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleScheduler {

    private final SimpleKafkaChannel simpleKafkaChannel;

    public SimpleScheduler(final SimpleKafkaChannel simpleKafkaChannel) {
        this.simpleKafkaChannel = simpleKafkaChannel;
    }

    @Scheduled(fixedDelay = 1000)
    public void publishSimpleMessage() {
        final SimpleMessage message = new SimpleMessage(UUID.randomUUID().toString());

        log.info("publishSimpleMessage={}", message);

        simpleKafkaChannel.output()
                .send(MessageBuilder.withPayload(message).build());
    }

}

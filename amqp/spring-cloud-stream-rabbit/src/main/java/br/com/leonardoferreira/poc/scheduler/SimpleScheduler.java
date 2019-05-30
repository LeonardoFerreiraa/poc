package br.com.leonardoferreira.poc.scheduler;

import br.com.leonardoferreira.poc.channel.SimpleChannel;
import br.com.leonardoferreira.poc.domain.SimpleMessage;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleScheduler {

    private final SimpleChannel simpleChannel;

    public SimpleScheduler(SimpleChannel simpleChannel) {
        this.simpleChannel = simpleChannel;
    }

    @Scheduled(fixedDelay = 1000)
    public void publishMessage() {
        log.info("publishMessage");

        Message<SimpleMessage> message = MessageBuilder
                .withPayload(new SimpleMessage(UUID.randomUUID().toString()))
                .build();

        simpleChannel.simpleOutput()
                .send(message);
    }

}

package br.com.leonardoferreira.poc.schedule;

import java.util.UUID;

import br.com.leonardoferreira.poc.domain.SimpleMessage;
import br.com.leonardoferreira.poc.repository.SimpleMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SimpleScheduler {

    private final KafkaTemplate<String, SimpleMessage> kafkaTemplate;

    private final SimpleMessageRepository simpleMessageRepository;

    @Transactional
    @Scheduled(fixedDelay = 10_000)
    public void produceMessage() {
        final SimpleMessage payload = new SimpleMessage(UUID.randomUUID().toString());

        final Message<SimpleMessage> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, "streaming.simple-app.simple-message.created")
                .setHeader(KafkaHeaders.MESSAGE_KEY, payload.getId())
                .build();

        kafkaTemplate.executeInTransaction(callback -> kafkaTemplate.send(message));

        simpleMessageRepository.save(payload);

        throw new RuntimeException(payload.getId());
    }

}

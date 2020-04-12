package br.com.leonardoferreira.poc.listener;

import br.com.leonardoferreira.poc.domain.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class SimpleListener {

    @Transactional
    @KafkaListener(topics = "streaming.simple-app.simple-message.created", groupId = "${spring.application.name}")
    public void listen(@Payload final Message<SimpleMessage> message) {
        log.info("Method=listen, simpleMessage={}", message.getPayload());
    }

}

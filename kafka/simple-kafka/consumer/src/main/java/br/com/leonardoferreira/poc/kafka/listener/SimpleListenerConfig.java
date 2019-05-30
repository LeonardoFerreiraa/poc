package br.com.leonardoferreira.poc.kafka.listener;

import br.com.leonardoferreira.poc.kafka.domain.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleListenerConfig {

    @KafkaListener(id = "simpleListener", topics = "simple.topic")
    public void simpleListener(@Payload String simpleMessage) {
        log.info("simpleListener, simpleMessage={}", simpleMessage);
    }

}

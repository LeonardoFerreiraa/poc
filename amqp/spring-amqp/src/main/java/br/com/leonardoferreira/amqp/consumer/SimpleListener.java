package br.com.leonardoferreira.amqp.consumer;

import br.com.leonardoferreira.amqp.domain.MessageSimple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleListener {

    @RabbitListener(queues = "simple.queue", concurrency = "10")
    public void receiveMessage(final MessageSimple messageSimple) {
        log.info("M=receiveMessage, messageSimple={}", messageSimple);
    }

}


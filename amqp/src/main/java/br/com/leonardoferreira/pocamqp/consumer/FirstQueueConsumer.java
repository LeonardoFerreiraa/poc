package br.com.leonardoferreira.pocamqp.consumer;

import br.com.leonardoferreira.pocamqp.domain.StatusMessage;
import br.com.leonardoferreira.pocamqp.config.FirstQueueConfig;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FirstQueueConsumer {

    @SneakyThrows
    @RabbitListener(queues = FirstQueueConfig.QUEUE_NAME,
            containerFactory = FirstQueueConfig.CONTAINER_FACTORY)
    public void receiveMessage(@Payload StatusMessage body) {
        log.info("Method=receiveMessage, message={}", body);
        if (body.getStatus().equals("nok")) {
            throw new RuntimeException();
        }

        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
    }

}

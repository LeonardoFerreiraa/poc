package br.com.leonardoferreira.poc.listener;

import br.com.leonardoferreira.poc.config.RabbitListenerWithRetry;
import br.com.leonardoferreira.poc.domain.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleListener {

    @RabbitListenerWithRetry(event = "my-event")
    public void listen(final SimpleMessage simpleMessage) {
        log.info("listen={}", simpleMessage);
//        throw new RuntimeException();
    }

}

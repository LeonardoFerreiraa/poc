package br.com.leonardoferreira.poc.listener;

import br.com.leonardoferreira.poc.channel.SimpleChannel;
import br.com.leonardoferreira.poc.domain.SimpleMessage;
import java.util.Random;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleListener {

    private Random random = new Random();

    @SneakyThrows
    @StreamListener(SimpleChannel.SIMPLE_INPUT)
    public void process(SimpleMessage simpleMessage) {
        Thread.sleep(5000);

        if (random.nextBoolean()) {
            log.info("error on {}", simpleMessage.getUuid());
            throw new RuntimeException(simpleMessage.getUuid());
        }

        log.info("simpleMessage={}", simpleMessage);
    }

}

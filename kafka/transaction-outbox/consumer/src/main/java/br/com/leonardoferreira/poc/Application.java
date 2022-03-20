package br.com.leonardoferreira.poc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@Slf4j
@Component
class MyConsummer {

    @KafkaListener(
            id = "my-listener",
            topics = "simple.topic"
    )
    public void listen(final SimpleMessage simpleMessage) {
        log.info("M=listen, simpleMessage={}", simpleMessage);
    }

}

@Data
class SimpleMessage {
    private String id;
}
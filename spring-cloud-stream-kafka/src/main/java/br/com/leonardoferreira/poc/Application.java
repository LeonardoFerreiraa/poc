package br.com.leonardoferreira.poc;

import br.com.leonardoferreira.poc.channel.SimpleKafkaChannel;
import br.com.leonardoferreira.poc.channel.SimpleRabbitChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableBinding({
        SimpleKafkaChannel.class,
        SimpleRabbitChannel.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

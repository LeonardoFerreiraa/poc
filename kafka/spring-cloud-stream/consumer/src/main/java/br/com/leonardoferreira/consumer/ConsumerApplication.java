package br.com.leonardoferreira.consumer;

import br.com.leonardoferreira.consumer.channel.CreatedUserChannel;
import br.com.leonardoferreira.consumer.channel.ReceivedCustomerChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({CreatedUserChannel.class, ReceivedCustomerChannel.class})
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}

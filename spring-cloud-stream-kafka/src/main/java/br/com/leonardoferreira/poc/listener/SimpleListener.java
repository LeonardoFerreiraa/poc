package br.com.leonardoferreira.poc.listener;

import br.com.leonardoferreira.poc.channel.SimpleKafkaChannel;
import br.com.leonardoferreira.poc.channel.SimpleRabbitChannel;
import br.com.leonardoferreira.poc.domain.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleListener {

    @SendTo(SimpleRabbitChannel.SIMPLE_RABBIT_OUTPUT)
    @StreamListener(SimpleKafkaChannel.SIMPLE_KAFKA_INPUT)
    public SimpleMessage simpleKafkaChannelInputListener(final SimpleMessage simpleMessage) throws Exception {
        Thread.sleep(1000);
        log.info("simpleKafkaChannelInputListener={}", simpleMessage);
        return simpleMessage;
    }

    @StreamListener(SimpleRabbitChannel.SIMPLE_RABBIT_INPUT)
    public void simpleRabbitChannelInputListener(final SimpleMessage simpleMessage) throws Exception {
        Thread.sleep(2000);
        log.info("simpleRabbitChannelInputListener={}", simpleMessage);
    }

}

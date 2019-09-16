package br.com.leonardoferreira.poc.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SimpleKafkaChannel {

    String SIMPLE_KAFKA_INPUT = "simple-kafka-input";

    String SIMPLE_KAFKA_OUTPUT = "simple-kafka-output";

    @Input(SIMPLE_KAFKA_INPUT)
    SubscribableChannel simpleInput();

    @Output(SIMPLE_KAFKA_OUTPUT)
    MessageChannel output();


}

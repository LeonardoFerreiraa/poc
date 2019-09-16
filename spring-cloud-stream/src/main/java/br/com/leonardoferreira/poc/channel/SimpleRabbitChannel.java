package br.com.leonardoferreira.poc.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SimpleRabbitChannel {

    String SIMPLE_RABBIT_INPUT = "simple-rabbit-input";

    String SIMPLE_RABBIT_OUTPUT = "simple-rabbit-output";

    @Input(SIMPLE_RABBIT_INPUT)
    SubscribableChannel input();

    @Output(SIMPLE_RABBIT_OUTPUT)
    MessageChannel output();

}

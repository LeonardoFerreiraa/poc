package br.com.leonardoferreira.poc.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SimpleChannel {

    String SIMPLE_INPUT = "simple-input";

    String SIMPLE_OUTPUT = "simple-output";

    @Output(SIMPLE_OUTPUT)
    MessageChannel simpleOutput();

    @Input(SIMPLE_INPUT)
    SubscribableChannel simpleInput();

}

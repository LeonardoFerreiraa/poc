package br.com.leonardoferreira.producer.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CreatedUserChannel {

    String CREATED_USER_OUTPUT = "created-user-output";

    @Output(CREATED_USER_OUTPUT)
    MessageChannel output();

}

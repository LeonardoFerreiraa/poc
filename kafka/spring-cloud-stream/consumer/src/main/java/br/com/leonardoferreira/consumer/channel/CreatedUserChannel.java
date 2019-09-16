package br.com.leonardoferreira.consumer.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CreatedUserChannel {

    String CREATED_USER_INPUT = "created-user-input";

    @Input(CREATED_USER_INPUT)
    SubscribableChannel input();

}

package br.com.leonardoferreira.consumer.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReceivedCustomerChannel {

    String RECEIVED_CUSTOMER_INPUT = "received-customer-input";

    @Input(RECEIVED_CUSTOMER_INPUT)
    SubscribableChannel input();
    
}

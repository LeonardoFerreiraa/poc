package br.com.leonardoferreira.consumer.listener;

import br.com.leonardoferreira.ReceivedCustomer;
import br.com.leonardoferreira.consumer.channel.ReceivedCustomerChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReceivedCustomerListener {

    @StreamListener(ReceivedCustomerChannel.RECEIVED_CUSTOMER_INPUT)
    public void listen(final ReceivedCustomer receivedCustomer) {
        log.info("M=listen, receivedCustomer={}", receivedCustomer);
    }

}


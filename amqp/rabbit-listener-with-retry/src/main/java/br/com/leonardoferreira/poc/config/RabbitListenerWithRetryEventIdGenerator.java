package br.com.leonardoferreira.poc.config;

import java.util.concurrent.atomic.AtomicInteger;

class RabbitListenerWithRetryEventIdGenerator {

    private final AtomicInteger counter = new AtomicInteger();

    String next() {
        return "org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#" + this.counter.getAndIncrement();
    }

}

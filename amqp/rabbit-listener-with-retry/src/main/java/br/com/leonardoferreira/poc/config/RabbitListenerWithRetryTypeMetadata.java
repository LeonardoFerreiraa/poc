package br.com.leonardoferreira.poc.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
class RabbitListenerWithRetryTypeMetadata {

    static final RabbitListenerWithRetryTypeMetadata EMPTY = new RabbitListenerWithRetryTypeMetadata();

    private final List<RabbiListenerWithRetryMethod> listenerMethods;

    private RabbitListenerWithRetryTypeMetadata() {
        this.listenerMethods = new ArrayList<>();
    }

    RabbitListenerWithRetryTypeMetadata(final List<RabbiListenerWithRetryMethod> methods) {
        this.listenerMethods = methods;
    }

}

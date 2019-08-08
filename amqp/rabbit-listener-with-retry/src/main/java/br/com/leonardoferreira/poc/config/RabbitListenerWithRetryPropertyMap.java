package br.com.leonardoferreira.poc.config;

import java.util.HashMap;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = "spring.rabbitmq.listener-with-retry")
public class RabbitListenerWithRetryPropertyMap {

    private HashMap<String, RabbitListenerWithRetryProperty> events;

    public RabbitListenerWithRetryProperty findByName(final String event) {
        return events.get(event);
    }

}

package br.com.leonardoferreira.poc.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitListenerWithRetryPropertyMap.class)
public class RabbitListenerWithRetryConfig {

    @Bean
    public MessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final MessageConverter jackson2JsonMessageConverter,
                                         final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerWithRetryAnnotationBeanPostProcessor rabbitListenerWithRetryAnnotationBeanPostProcessor(
            final RabbitListenerWithRetryPropertyMap rabbitListenerWithRetryPropertyMap,
            final RabbitTemplate rabbitTemplate
    ) {
        return new RabbitListenerWithRetryAnnotationBeanPostProcessor(rabbitListenerWithRetryPropertyMap, rabbitTemplate);
    }

}

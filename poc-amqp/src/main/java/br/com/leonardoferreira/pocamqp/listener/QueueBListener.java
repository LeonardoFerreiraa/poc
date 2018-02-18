package br.com.leonardoferreira.pocamqp.listener;

import br.com.leonardoferreira.pocamqp.domain.QueueName;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QueueBListener {

    private static final QueueName queueName = QueueName.QUEUE_B;

    @Bean
    public Queue queueB() {
        return new Queue(queueName.getName(), false);
    }

    @Bean
    public TopicExchange queueBExchange() {
        return new TopicExchange(queueName.getExchange());
    }

    @Bean
    public Binding queueBBinding(Queue queueB, TopicExchange queueBExchange) {
        return BindingBuilder
                .bind(queueB)
                .to(queueBExchange)
                .with(queueName.getName());
    }

    @Bean
    public SimpleMessageListenerContainer queueBContainer(ConnectionFactory connectionFactory, MessageListenerAdapter queueListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName.getName());
        container.setMessageListener(queueListener);
        container.setConcurrentConsumers(2);
        return container;
    }

}
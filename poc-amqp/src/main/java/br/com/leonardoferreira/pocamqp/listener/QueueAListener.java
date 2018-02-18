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
public class QueueAListener {

    private static final QueueName queueName = QueueName.QUEUE_A;

    @Bean
    public Queue queueA() {
        return new Queue(queueName.getName(), false);
    }

    @Bean
    public TopicExchange queueAExchange() {
        return new TopicExchange(queueName.getExchange());
    }

    @Bean
    public Binding queueABinding(Queue queueA, TopicExchange queueAExchange) {
        return BindingBuilder
                .bind(queueA)
                .to(queueAExchange)
                .with(queueName.getName());
    }

    @Bean
    public SimpleMessageListenerContainer queueAContainer(ConnectionFactory connectionFactory, MessageListenerAdapter queueListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName.getName());
        container.setMessageListener(queueListener);
        return container;
    }

}
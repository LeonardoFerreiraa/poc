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
public class QueueDListener {

    private static final QueueName queueName = QueueName.QUEUE_D;

    @Bean
    public Queue queueD() {
        return new Queue(queueName.getName(), false);
    }

    @Bean
    public TopicExchange queueDExchange() {
        return new TopicExchange(queueName.getExchange());
    }

    @Bean
    public Binding queueDBinding(Queue queueD, TopicExchange queueDExchange) {
        return BindingBuilder
                .bind(queueD)
                .to(queueDExchange)
                .with(queueName.getName());
    }

    @Bean
    public SimpleMessageListenerContainer queueDContainer(ConnectionFactory connectionFactory, MessageListenerAdapter queueListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName.getName());
        container.setMessageListener(queueListener);
        return container;
    }

}
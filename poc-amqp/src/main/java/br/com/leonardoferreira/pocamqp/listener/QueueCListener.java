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
public class QueueCListener {

    private static final QueueName queueName = QueueName.QUEUE_C;

    @Bean
    public Queue queueC() {
        return new Queue(queueName.getName(), false);
    }

    @Bean
    public TopicExchange queueCExchange() {
        return new TopicExchange(queueName.getExchange());
    }

    @Bean
    public Binding queueCBinding(Queue queueC, TopicExchange queueCExchange) {
        return BindingBuilder
                .bind(queueC)
                .to(queueCExchange)
                .with(queueName.getName());
    }

    @Bean
    public SimpleMessageListenerContainer queueCContainer(ConnectionFactory connectionFactory, MessageListenerAdapter queueListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName.getName());
        container.setMessageListener(queueListener);
        container.setConcurrentConsumers(3);
        return container;
    }

}
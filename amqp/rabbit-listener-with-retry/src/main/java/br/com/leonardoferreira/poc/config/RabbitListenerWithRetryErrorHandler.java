package br.com.leonardoferreira.poc.config;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;

public class RabbitListenerWithRetryErrorHandler implements RabbitListenerErrorHandler {

    private final AmqpTemplate amqpTemplate;

    private final RabbitListenerWithRetry rabbitListener;

    private final RabbitListenerWithRetryProperty property;

    public RabbitListenerWithRetryErrorHandler(final AmqpTemplate amqpTemplate,
                                               final RabbitListenerWithRetry rabbitListener,
                                               final RabbitListenerWithRetryProperty property) {
        this.amqpTemplate = amqpTemplate;
        this.rabbitListener = rabbitListener;
        this.property = property;
    }

    @Override
    public Object handleError(final Message amqpMessage,
                              final org.springframework.messaging.Message<?> message,
                              final ListenerExecutionFailedException exception) throws Exception {
        // TODO
        throw new AmqpRejectAndDontRequeueException(exception);
    }

}

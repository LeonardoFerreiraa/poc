package br.com.leonardoferreira.poc.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;

class RabbitListenerWithRetryErrorHandler implements RabbitListenerErrorHandler {

    private final AmqpTemplate amqpTemplate;

    private final RabbitListenerWithRetry rabbitListener;

    private final RabbitListenerWithRetryProperty property;

    RabbitListenerWithRetryErrorHandler(final AmqpTemplate amqpTemplate,
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
        if (isDiscardException(exception)) {
            throw new ImmediateAcknowledgeAmqpException(exception);
        }

        final int qtyRetry = countDeath(amqpMessage);
        if ((qtyRetry >= rabbitListener.maxAttempts() || isDlqException(exception)) && property.isCreateDlq()) {
            sendToDlq(amqpMessage, exception);
        } else if (property.isCreateRetry()) {
            sendToRetry(amqpMessage, exception, qtyRetry);
        }

        throw new ImmediateAcknowledgeAmqpException(exception);
    }

    private void sendToDlq(final Message amqpMessage, final ListenerExecutionFailedException exception) {
        new RepublishMessageRecoverer(
                amqpTemplate,
                property.getExchange(),
                property.getDlq()
        ).recover(amqpMessage, exception);
    }

    private void sendToRetry(final Message amqpMessage, final ListenerExecutionFailedException exception, final int qtyRetry) {
        final String expiration = String.valueOf(rabbitListener.ttlRetry() * qtyRetry);
        amqpMessage.getMessageProperties().setExpiration(expiration);

        new RepublishMessageRecoverer(
                amqpTemplate,
                property.getExchange(),
                property.getRetry()
        ).recover(amqpMessage, exception);
    }

    private boolean isDlqException(final ListenerExecutionFailedException exception) {
        final Class<? extends Throwable> cause = exception.getCause().getClass();
        return Arrays.asList(rabbitListener.dlqWhen()).contains(cause);
    }

    private boolean isDiscardException(final ListenerExecutionFailedException exception) {
        final Class<? extends Throwable> cause = exception.getCause().getClass();
        return Arrays.asList(rabbitListener.discardWhen()).contains(cause);
    }

    private int countDeath(final Message message) {
        int count = 0;
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        if (headers.containsKey("x-death")) {
            count = Integer.parseInt(getXDeath(headers).get("count").toString());
        }
        return ++count;
    }

    private Map getXDeath(final Map<String, Object> headers) {
        final List list = (List) Collections.singletonList(headers.get("x-death")).get(0);
        return (Map) list.get(0);
    }

}

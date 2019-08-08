package br.com.leonardoferreira.poc.config;

import java.lang.reflect.Method;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

class RabbitListenerWithRetryHandlerMethodFactoryAdapter implements MessageHandlerMethodFactory {

    private final BeanFactory beanFactory;

    private final MessageHandlerMethodFactory messageHandlerMethodFactory;

    RabbitListenerWithRetryHandlerMethodFactoryAdapter(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.messageHandlerMethodFactory = createDefaultMessageHandlerMethodFactory();
    }

    @Override
    public InvocableHandlerMethod createInvocableHandlerMethod(final Object bean, final Method method) {
        return messageHandlerMethodFactory.createInvocableHandlerMethod(bean, method);
    }

    private MessageHandlerMethodFactory createDefaultMessageHandlerMethodFactory() {
        final DefaultMessageHandlerMethodFactory defaultFactory = new DefaultMessageHandlerMethodFactory();

        defaultFactory.setBeanFactory(this.beanFactory);
        defaultFactory.afterPropertiesSet();

        return defaultFactory;
    }
}

package br.com.leonardoferreira.poc.httpclient.configuration;

import br.com.leonardoferreira.poc.httpclient.ClientBuilder;
import org.springframework.beans.factory.FactoryBean;

public class HttpClientFactory implements FactoryBean<Object> {

    private Class<?> type;

    @Override
    public Object getObject() throws Exception {
        return ClientBuilder.of(type)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

}
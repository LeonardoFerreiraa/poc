package br.com.leonardoferreira.poc.httpclient;

import br.com.leonardoferreira.poc.httpclient.annotation.HttpClient;
import br.com.leonardoferreira.poc.httpclient.client.Client;
import br.com.leonardoferreira.poc.httpclient.client.impl.ReactorClient;
import br.com.leonardoferreira.poc.httpclient.handler.ReactorHandler;
import br.com.leonardoferreira.poc.httpclient.request.RequestBuilder;
import br.com.leonardoferreira.poc.httpclient.request.impl.SpringRequestBuilder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ClientBuilder<T> {

    private Class<T> targetClass;

    private RequestBuilder requestBuilder;

    private Client<?> client;

    private InvocationHandler handler;

    private ClientBuilder(Class<T> clazz) {
        this.targetClass = clazz;
    }

    public static <T> ClientBuilder<T> of(Class<T> clazz) {
        return new ClientBuilder<>(clazz);
    }

    public ClientBuilder<T> template(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
        return this;
    }

    public ClientBuilder<T> client(Client client) {
        this.client = client;
        return this;
    }

    public T build() {
        HttpClient httpClient = this.targetClass.getAnnotation(HttpClient.class);

        if (this.requestBuilder == null) {
            this.requestBuilder = new SpringRequestBuilder();
        }

        if (this.client == null) {
            this.client = new ReactorClient(httpClient.url());
        }

        if (this.handler == null) {
            this.handler = new ReactorHandler(this.targetClass, this.client, this.requestBuilder);
        }

        Object clientImpl = Proxy.newProxyInstance(
                this.targetClass.getClassLoader(),
                new Class[]{this.targetClass},
                this.handler);

        return this.targetClass.cast(clientImpl);
    }

}

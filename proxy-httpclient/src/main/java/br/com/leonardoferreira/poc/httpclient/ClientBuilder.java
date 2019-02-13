package br.com.leonardoferreira.poc.httpclient;

import java.lang.reflect.Proxy;

public class ClientBuilder<T> {

    private Class<T> client;

    private ClientBuilder(Class<T> clazz) {
        this.client = clazz;
    }

    public static <T> ClientBuilder<T> of(Class<T> clazz) {
        return new ClientBuilder<>(clazz);
    }

    public T build() {
        Object clientImpl = Proxy.newProxyInstance(client.getClassLoader(),
                new Class[]{client},
                new GenericClientImpl());

        return client.cast(clientImpl);
    }

}

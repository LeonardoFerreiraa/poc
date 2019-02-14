package br.com.leonardoferreira.poc.httpclient;

import br.com.leonardoferreira.poc.httpclient.annotation.Client;
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
        Client client = this.client.getAnnotation(Client.class);
        GenericClientImpl genericClient = new GenericClientImpl(client.url());

        Object clientImpl = Proxy.newProxyInstance(this.client.getClassLoader(), new Class[]{this.client}, genericClient);

        return this.client.cast(clientImpl);
    }

}

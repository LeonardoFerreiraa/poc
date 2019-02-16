package br.com.leonardoferreira.poc.httpclient.handler;

import br.com.leonardoferreira.poc.httpclient.client.Client;
import br.com.leonardoferreira.poc.httpclient.request.RequestAttributes;
import br.com.leonardoferreira.poc.httpclient.request.RequestBuilder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.reactivestreams.Publisher;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorHandler implements InvocationHandler {

    private Client<Mono<ClientResponse>> client;

    private RequestBuilder requestBuilder;

    public ReactorHandler(Client<?> client, RequestBuilder requestBuilder) {
        this.client = (Client<Mono<ClientResponse>>) client;
        this.requestBuilder = requestBuilder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Type type = method.getGenericReturnType();
        if (!(type instanceof ParameterizedType)) {
            return null;
        }

        ParameterizedType parameterizedType = (ParameterizedType) type;
        if (!Publisher.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
            return null;
        }

        RequestAttributes requestAttributes = requestBuilder.build(method, args);
        if (requestAttributes == null) {
            return null;
        }

        Mono<ClientResponse> clientResponse = client.doRequest(requestAttributes);

        if (parameterizedType.getRawType().equals(Flux.class)) {
            Class<?> returnType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            return clientResponse.flatMapMany(it -> it.bodyToFlux(returnType));
        }

        Class<?> returnType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        return clientResponse.flatMap(it -> it.bodyToMono(returnType));
    }

}

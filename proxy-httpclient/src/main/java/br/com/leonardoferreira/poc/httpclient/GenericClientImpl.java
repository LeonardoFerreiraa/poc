package br.com.leonardoferreira.poc.httpclient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.reactivestreams.Publisher;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GenericClientImpl implements InvocationHandler {

    private WebClient webClient;

    public GenericClientImpl(String url) {
        this.webClient = WebClient.create(url);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            return null;
        }

        Type type = method.getGenericReturnType();
        if (!(type instanceof ParameterizedType)) {
            return null;
        }

        ParameterizedType parameterizedType = (ParameterizedType) type;
        if (!Publisher.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
            return null;
        }

        BodyInserter<?, ? super ClientHttpRequest> body = buildBody(method, args);

        if (parameterizedType.getRawType().equals(Flux.class)) {
            Class<?> returnType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            return doRequest(requestMapping, body)
                    .flatMapMany(it -> it.bodyToFlux(returnType));
        }

        Class<?> returnType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        return doRequest(requestMapping, body)
                .flatMap(it -> it.bodyToMono(returnType));
    }

    private Mono<ClientResponse> doRequest(RequestMapping requestMapping, BodyInserter<?, ? super ClientHttpRequest> body) {
        return webClient
                .method(buildMethod(requestMapping))
                .uri(buildUri(requestMapping))
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .exchange();
    }

    private BodyInserter<?, ? super ClientHttpRequest> buildBody(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(RequestBody.class)) {
                return BodyInserters.fromObject(args[i]);
            }
        }

        return null;
    }

    private HttpMethod buildMethod(RequestMapping requestMapping) {
        return requestMapping.method().length <= 1 ? HttpMethod.GET
                : HttpMethod.resolve(requestMapping.method()[0].name());
    }

    private String buildUri(RequestMapping requestMapping) {
        return requestMapping.value().length == 0 ? "/" : requestMapping.value()[0];
    }

}

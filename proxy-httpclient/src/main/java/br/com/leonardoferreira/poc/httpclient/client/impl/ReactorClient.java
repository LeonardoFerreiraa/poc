package br.com.leonardoferreira.poc.httpclient.client.impl;

import br.com.leonardoferreira.poc.httpclient.client.Client;
import br.com.leonardoferreira.poc.httpclient.request.RequestAttributes;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ReactorClient implements Client<Mono<ClientResponse>> {

    private WebClient webClient;

    public ReactorClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public Mono<ClientResponse> doRequest(RequestAttributes requestAttributes) {
        return webClient
                .method(convertMethod(requestAttributes))
                .uri(requestAttributes.getUri())
                .body(convertBody(requestAttributes))
                .headers(convertHeader(requestAttributes))
                .attributes(convertAttributes(requestAttributes))
                .exchange();
    }

    private Consumer<Map<String, Object>> convertAttributes(RequestAttributes requestAttributes) {
        return params -> requestAttributes.getAttributes()
                .forEach(params::put);
    }

    private Consumer<HttpHeaders> convertHeader(RequestAttributes requestAttributes) {
        return headers -> requestAttributes.getHeaders()
                .forEach((k, v) -> headers.put(k, Collections.singletonList(v)));
    }

    private BodyInserter<Object, ReactiveHttpOutputMessage> convertBody(RequestAttributes requestAttributes) {
        return requestAttributes.getBody() == null ?
                null : BodyInserters.fromObject(requestAttributes.getBody());
    }

    private HttpMethod convertMethod(RequestAttributes requestAttributes) {
        return HttpMethod.resolve(requestAttributes.getMethod().name());
    }

}

package br.com.leonardoferreira.webflux.service;

import br.com.leonardoferreira.webflux.domain.AggregatedResponse;
import br.com.leonardoferreira.webflux.domain.AnythingRequest;
import br.com.leonardoferreira.webflux.domain.AnythingResponse;
import br.com.leonardoferreira.webflux.domain.TodoResponse;
import br.com.leonardoferreira.webflux.domain.UuidResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AggregateService {

    private final WebClient httpBinClient;

    private final WebClient jsonPlaceHolderClient;

    public AggregateService(final WebClient httpBinClient,
                            final WebClient jsonPlaceHolderClient) {
        this.httpBinClient = httpBinClient;
        this.jsonPlaceHolderClient = jsonPlaceHolderClient;
    }

    public Mono<AggregatedResponse> call() {
        final Mono<TodoResponse> todoResponseMono = httpBinClient.post()
                .uri("/anything")
                .body(Mono.just(new AnythingRequest(200)), AnythingRequest.class)
                .retrieve()
                .bodyToMono(AnythingResponse.class)
                .map(AnythingResponse::getJson)
                .map(AnythingResponse.Json::getNumber)
                .flatMap(this::retrieveTodo);

        final Mono<UuidResponse> uuidResponseMono = httpBinClient.get()
                .uri("/uuid")
                .retrieve()
                .bodyToMono(UuidResponse.class);

        return Mono.zip(todoResponseMono, uuidResponseMono, this::buildAggregatedResponse);
    }

    private AggregatedResponse buildAggregatedResponse(final TodoResponse todoResponse, final UuidResponse uuidResponse) {
        return new AggregatedResponse(
                todoResponse.getTitle(),
                uuidResponse.getUuid()
        );
    }

    private Mono<TodoResponse> retrieveTodo(final Integer number) {
        return jsonPlaceHolderClient.get()
                .uri(uriBuilder -> uriBuilder.path("/todos/{number}").build(number))
                .retrieve()
                .bodyToMono(TodoResponse.class);
    }


}

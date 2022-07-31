package br.com.leonardoferreira.poc.webclient.service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import br.com.leonardoferreira.poc.webclient.domain.ComplexThing;
import br.com.leonardoferreira.poc.webclient.domain.SimpleThing;
import br.com.leonardoferreira.poc.webclient.domain.response.RandomResponse;
import br.com.leonardoferreira.poc.webclient.domain.response.UuidResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@Slf4j
@Service
public class AnythingService {

    private final Random random;

    private final WebClient httpBinWebClient;

    private final WebClient localHostClient;

    public AnythingService(final WebClient httpBinWebClient,
                           final WebClient localHostClient) {
        this.httpBinWebClient = httpBinWebClient;
        this.localHostClient = localHostClient;
        this.random = new Random();
    }

    public Mono<SimpleThing> findSimpleThing() {
        return httpBinWebClient.get()
                .uri("/uuid")
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(UuidResponse.class))
                .map(uuidResponse -> new SimpleThing(uuidResponse.getUuid()));
    }

    public Mono<ComplexThing> findComplexThing() {
        final Mono<UuidResponse> firstCall = httpBinWebClient.get()
                .uri("/uuid")
                .retrieve()
                .bodyToMono(UuidResponse.class)
                .doOnSuccess(i -> log.info("success firstCall"));

        final Mono<UuidResponse> secondCall = httpBinWebClient.get()
                .uri("/status/500")
                .retrieve()
                .bodyToMono(UuidResponse.class)
                .onErrorResume(throwable ->
                        Mono.just(UUID.randomUUID())
                                .map(uuid -> new UuidResponse(uuid.toString()))
                                .doOnSuccess(uuid -> log.info("success on fallback"))
                )
                .doOnSuccess(i -> log.info("success secondCall"));

        final Mono<Long> thirdCall = localHostClient.get()
                .uri("/randoms")
                .retrieve()
                .bodyToMono(RandomResponse.class)
                .map(randomResponse -> randomResponse.getRandomLong() * random.nextLong())
                .doOnSuccess(i -> log.info("success randomCall"));

        return Mono.zip(firstCall, secondCall, thirdCall)
                .map(this::mapToComplexThing);
    }

    public Mono<SimpleThing> notFound() {
        return httpBinWebClient.get()
                .uri("/status/404")
                .retrieve()
                .bodyToMono(UuidResponse.class)
                .map(uuidResponse -> new SimpleThing(uuidResponse.getUuid()));
    }

    private ComplexThing mapToComplexThing(final Tuple3<UuidResponse, UuidResponse, Long> result) {
        final UuidResponse resultT1 = result.getT1();
        final UuidResponse resultT2 = result.getT2();
        final Long resultT3 = result.getT3();

        return ComplexThing.builder()
                .first(resultT1.getUuid())
                .second(resultT2.getUuid())
                .calculatedData(resultT3)
                .creation(LocalDateTime.now())
                .build();
    }
}

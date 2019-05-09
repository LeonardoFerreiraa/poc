package br.com.leonardoferreira.poc.webclient.service;

import br.com.leonardoferreira.poc.webclient.domain.ComplexThing;
import br.com.leonardoferreira.poc.webclient.domain.SimpleThing;
import br.com.leonardoferreira.poc.webclient.domain.response.RandomResponse;
import br.com.leonardoferreira.poc.webclient.domain.response.UuidResponse;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AnythingService {

    private final Random random;

    public AnythingService() {
        this.random = new Random();
    }

    public Mono<SimpleThing> findSimpleThing() {
        WebClient client = WebClient.builder()
                .baseUrl("https://httpbin.org")
                .build();

        return client.get()
                .uri("/uuid")
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(UuidResponse.class))
                .map(uuidResponse -> new SimpleThing(uuidResponse.getUuid()));
    }

    public Mono<ComplexThing> findComplexThing() {
        WebClient localHostClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        WebClient httpbinClient = WebClient.builder()
                .baseUrl("https://httpbin.org")
                .build();

        Mono<UuidResponse> firstCall = httpbinClient.get()
                .uri("/uuid")
                .retrieve()
                .bodyToMono(UuidResponse.class)
                .doOnSuccess(i -> log.info("success firstCall"));

        Mono<UuidResponse> secondCall = httpbinClient.get()
                .uri("/status/500")
                .retrieve()
                .bodyToMono(UuidResponse.class)
                .onErrorResume(throwable ->
                        Mono.just(UUID.randomUUID())
                                .map(uuid -> new UuidResponse(uuid.toString()))
                                .doOnSuccess(uuid -> log.info("success on fallback"))
                )
                .doOnSuccess(i -> log.info("success secondCall"));

        Mono<Long> thirdCall = localHostClient.get()
                .uri("/randoms")
                .retrieve()
                .bodyToMono(RandomResponse.class)
                .map(randomResponse -> randomResponse.getRandomLong() * random.nextLong())
                .doOnSuccess(i -> log.info("success randomCall"));

        return Mono.zip(firstCall, secondCall, thirdCall)
                .map(result -> {
                    UuidResponse resultT1 = result.getT1();
                    UuidResponse resultT2 = result.getT2();
                    Long resultT3 = result.getT3();

                    ComplexThing complexThing = new ComplexThing();
                    complexThing.setFirst(resultT1.getUuid());
                    complexThing.setSecond(resultT2.getUuid());
                    complexThing.setCalculatedData(resultT3);
                    complexThing.setCreation(LocalDateTime.now());

                    return complexThing;
                });
    }

}

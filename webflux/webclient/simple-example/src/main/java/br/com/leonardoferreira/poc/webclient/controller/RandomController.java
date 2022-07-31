package br.com.leonardoferreira.poc.webclient.controller;

import br.com.leonardoferreira.poc.webclient.domain.response.RandomResponse;
import java.time.Duration;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/randoms")
public class RandomController {

    private final Random random;

    public RandomController() {
        this.random = new Random();
    }

    @GetMapping
    public Mono<RandomResponse> random() {
        return Mono
                .just(new RandomResponse(random.nextLong()))
                .delayElement(Duration.ofSeconds(random.nextInt(3)))
                .doOnSuccess(random -> {
                    log.info("Random: {}", random);
                });
    }

}

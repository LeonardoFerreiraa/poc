package br.com.leonardoferreira.poc.httpclient;

import br.com.leonardoferreira.poc.httpclient.client.GoogleClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoogleHttpClientTest {

    private GoogleClient googleClient;

    @BeforeEach
    void setUp() {
        googleClient = ClientBuilder
                .of(GoogleClient.class)
                .build();
    }

    @Test
    void printsHomePage() {
        Mono<String> homePage = googleClient.homePage();

        homePage.subscribe(System.out::println);

        StepVerifier
                .create(homePage)
                .expectNextCount(1)
                .verifyComplete();
    }
}

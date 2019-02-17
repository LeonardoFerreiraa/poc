package br.com.leonardoferreira.poc.httpclient;

import br.com.leonardoferreira.poc.httpclient.client.GoogleClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoogleHttpClientTest {

    @Autowired
    private GoogleClient googleClient;

    @Test
    void printsHomePage() {
        googleClient.toString();
        Mono<String> homePage = googleClient.homePage();

        homePage.subscribe(System.out::println);

        StepVerifier
                .create(homePage)
                .expectNextCount(1)
                .verifyComplete();
    }

}

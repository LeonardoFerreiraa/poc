package br.com.leonardoferreira.poc.httpclient;

import br.com.leonardoferreira.poc.httpclient.client.HttpBinClient;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.HttpBinRequest;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.HttpBinResponse;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.IpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpBinHttpClientTest {

    @Autowired
    private HttpBinClient httpBinClient;

    @Test
    void printsIp() {
        Mono<IpResponse> result = httpBinClient.retrieveIP();

        result.subscribe(System.out::println);

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void postRequest() {
        HttpBinRequest request = new HttpBinRequest("test-name");

        Mono<String> result = httpBinClient.anything(request, "application/json");

        result.subscribe(System.out::println);

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}

package br.com.leonardoferreira.poc.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListContactIntegrationTest {

    @LocalServerPort
    private Integer port;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void findAll() {
        client.get()
                .uri("/contacts")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("[0].name").value(Matchers.is("contact2"));
    }
}

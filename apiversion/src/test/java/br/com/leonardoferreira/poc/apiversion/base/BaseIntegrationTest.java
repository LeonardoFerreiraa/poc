package br.com.leonardoferreira.poc.apiversion.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.web.server.LocalServerPort;

public class BaseIntegrationTest {

    @LocalServerPort
    private Integer localPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = localPort;
    }
}

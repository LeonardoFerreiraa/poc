package br.com.leonardoferreira.poc.integration;

import br.com.leonardoferreira.poc.testlibrary.factory.ContactFactory;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@RequiredArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindAllContactIntegrationTest {

    private final ContactFactory contactFactory;

    @Test
    void shouldReturnAllContacts() {
        contactFactory.create(5);

        // @formatter:off
        RestAssured
                .given()
                .when()
                    .get("/contacts")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("$", Matchers.hasSize(5));
        // @formatter:on
    }

}

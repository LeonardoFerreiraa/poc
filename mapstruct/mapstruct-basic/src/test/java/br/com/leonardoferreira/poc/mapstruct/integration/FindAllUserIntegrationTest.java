package br.com.leonardoferreira.poc.mapstruct.integration;

import br.com.leonardoferreira.poc.mapstruct.factory.UserFactory;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindAllUserIntegrationTest {

    private final UserFactory userFactory;

    @Autowired
    FindAllUserIntegrationTest(final UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Test
    void findAll() {
        userFactory.create(5);

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("/users")
                .then()
                    .log().all()
                    .body("totalElements", Matchers.is(5));
        // @formatter:on
    }
}

package br.com.leonardoferreira.poc.mapstruct.integration;

import br.com.leonardoferreira.poc.mapstruct.domain.entity.User;
import br.com.leonardoferreira.poc.mapstruct.factory.UserFactory;
import br.com.leonardoferreira.poc.mapstruct.matcher.LongMatcher;
import io.restassured.RestAssured;
import java.time.format.DateTimeFormatter;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindUserByIdIntegrationTest {

    private final UserFactory userFactory;

    @Autowired
    FindUserByIdIntegrationTest(final UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Test
    void findById() {
        final User user = userFactory.create();

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("/users/1")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .body("id", LongMatcher.is(user.getId()))
                    .body("fullName", Matchers.is(user.getFirstName() + " " + user.getLastName()))
                    .body("title", LongMatcher.is(user.getTitle().getId()))
                    .body("address", Matchers.is(user.getAddress()))
                    .body("createdAt", Matchers.is(user.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))))
                    .body("createdAt", Matchers.is(user.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        // @formatter:on
    }

    @Test
    void findByIdNotFound() {
        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("/users/1")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
        // @formatter:on

    }
}

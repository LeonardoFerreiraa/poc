package br.com.leonardoferreira.poc.apiversion.integration.v1.header;

import br.com.leonardoferreira.poc.apiversion.base.BaseIntegrationTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactTest extends BaseIntegrationTest {

    @Test
    void contentNegotiationTest() {
        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                    .header("Accept", "application/vnd.poc.v1+json")
                .when()
                    .get("/contacts")
                .then()
                    .log().all()
                    .body("$", Matchers.hasSize(2))
                    .body("[0].name", Matchers.is("Foo"));
        // @formatter:on
    }
}

package br.com.leonardoferreira.poc.apiversion.integration.v2.url;

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
    void list() {
        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("/v2/contacts")
                .then()
                    .log().all()
                    .body("$", Matchers.hasSize(2))
                    .body("[0].fullName", Matchers.is("Foo Silva"))
                    .body("[0].email", Matchers.is("foo@silva.com"));
        // @formatter:on
    }
}

package br.com.leonardoferreira.poc.restdocs.integration;


import br.com.leonardoferreira.poc.restdocs.factory.ContactFactory;
import br.com.leonardoferreira.poc.restdocs.integration.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchContactIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private RequestSpecification documentationSpec;

    @Test
    public void findAll() {
        contactFactory.create(5);

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("/contacts")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK);
        // @formatter:on
    }

}


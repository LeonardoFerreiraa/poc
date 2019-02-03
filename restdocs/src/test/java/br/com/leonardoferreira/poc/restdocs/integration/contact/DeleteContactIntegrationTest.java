package br.com.leonardoferreira.poc.restdocs.integration.contact;

import br.com.leonardoferreira.poc.restdocs.factory.ContactFactory;
import br.com.leonardoferreira.poc.restdocs.integration.BaseIntegrationTest;
import br.com.leonardoferreira.poc.restdocs.repository.ContactRepository;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteContactIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void deleteContact() {
        contactFactory.create();

        // @formatter:off
        RestAssured
                .given(super.documentSpecification)
                    .log().all()
                    .filter(RestAssuredRestDocumentation.document("{ClassName}/{methodName}",
                            RequestDocumentation.pathParameters(
                                    RequestDocumentation.parameterWithName("id").description("Contact unique identifier"))))
                .when()
                    .delete("/contacts/{id}", 1L)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
        // @formatter:on

        Assertions.assertEquals(0, contactRepository.count());
    }

    @Test
    public void deleteNotFoundContact() {
        // @formatter:off
        RestAssured
                .given(super.documentSpecification)
                    .log().all()
                    .filter(RestAssuredRestDocumentation.document("{ClassName}/{methodName}",
                            PayloadDocumentation.responseFields(notFoundFields())))
                .when()
                    .delete("/contacts/{id}", 1L)
                .then()
                    .log().all()
                    .spec(notFoundSpec());
        // @formatter:on
    }
}

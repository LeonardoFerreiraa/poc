package br.com.leonardoferreira.poc.restdocs.integration.contact;

import br.com.leonardoferreira.poc.restdocs.extension.DocumentSpecification;
import br.com.leonardoferreira.poc.restdocs.extension.DocumentSpecificationExtension;
import br.com.leonardoferreira.poc.restdocs.factory.ContactFactory;
import br.com.leonardoferreira.poc.restdocs.fielddescriptor.CommonFieldDescriptor;
import br.com.leonardoferreira.poc.restdocs.repository.ContactRepository;
import br.com.leonardoferreira.poc.restdocs.specification.CommonResponseSpecification;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;

@ExtendWith(DocumentSpecificationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteContactIntegrationTest {

    private final ContactFactory contactFactory;

    private final ContactRepository contactRepository;

    @Autowired
    DeleteContactIntegrationTest(final ContactFactory contactFactory,
                                 final ContactRepository contactRepository) {
        this.contactFactory = contactFactory;
        this.contactRepository = contactRepository;
    }

    @Test
    void deleteContact(@DocumentSpecification final RequestSpecification documentSpecification) {
        contactFactory.create();

        // @formatter:off
        RestAssured
                .given(documentSpecification)
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
    void deleteNotFoundContact(@DocumentSpecification final RequestSpecification documentSpecification) {
        // @formatter:off
        RestAssured
                .given(documentSpecification)
                    .log().all()
                    .filter(
                            RestAssuredRestDocumentation.document(
                            "{ClassName}/{methodName}",
                            PayloadDocumentation.responseFields(CommonFieldDescriptor.notFound())
                            )
                    )
                .when()
                    .delete("/contacts/{id}", 1L)
                .then()
                    .log().all()
                    .spec(CommonResponseSpecification.notFound());
        // @formatter:on
    }
}

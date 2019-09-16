package br.com.leonardoferreira.poc.restdocs.integration.contact;

import br.com.leonardoferreira.poc.restdocs.ConstrainedFields;
import br.com.leonardoferreira.poc.restdocs.domain.Contact;
import br.com.leonardoferreira.poc.restdocs.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.restdocs.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.restdocs.extension.DocumentSpecification;
import br.com.leonardoferreira.poc.restdocs.extension.DocumentSpecificationExtension;
import br.com.leonardoferreira.poc.restdocs.factory.ContactFactory;
import br.com.leonardoferreira.poc.restdocs.factory.ContactRequestFactory;
import br.com.leonardoferreira.poc.restdocs.fielddescriptor.CommonFieldDescriptor;
import br.com.leonardoferreira.poc.restdocs.repository.ContactRepository;
import br.com.leonardoferreira.poc.restdocs.specification.CommonResponseSpecification;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
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
class UpdateContactIntegrationTest {

    private final ContactFactory contactFactory;

    private final ContactRequestFactory contactRequestFactory;

    private final ContactRepository contactRepository;

    @Autowired
    UpdateContactIntegrationTest(final ContactFactory contactFactory,
                                 final ContactRequestFactory contactRequestFactory,
                                 final ContactRepository contactRepository) {
        this.contactFactory = contactFactory;
        this.contactRequestFactory = contactRequestFactory;
        this.contactRepository = contactRepository;
    }

    @Test
    void updateContact(@DocumentSpecification final RequestSpecification documentSpecification) {
        contactFactory.create();
        ContactRequest request = contactRequestFactory.build();

        ConstrainedFields fields = new ConstrainedFields(ContactRequest.class);

        // @formatter:off
        RestAssured
                .given(documentSpecification)
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .filter(RestAssuredRestDocumentation.document("{ClassName}/{methodName}",
                            PayloadDocumentation.requestFields(
                                    fields.withPath("name").description("Contact name"),
                                    fields.withPath("email").description("Contact email")
                            ),
                            RequestDocumentation.pathParameters(
                                    RequestDocumentation.parameterWithName("id").description("Contact unique identifier"))))
                .when()
                    .put("/contacts/{id}", 1L)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
        // @formatter:on

        Contact contact = contactRepository.findById(1L)
                .orElseThrow(ResourceNotFoundException::new);

        Assertions.assertAll("Contact content",
                () -> Assertions.assertEquals(request.getName(), contact.getName()),
                () -> Assertions.assertEquals(request.getEmail(), contact.getEmail()));
    }

    @Test
    void failInValidations(@DocumentSpecification final RequestSpecification documentSpecification) {
        contactFactory.create();

        // @formatter:off
        RestAssured
                .given(documentSpecification)
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(new ContactRequest())
                    .filter(RestAssuredRestDocumentation.document("{ClassName}/{methodName}"))
                .when()
                    .put("/contacts/{id}", 1L)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("errors.find { it.field == 'email' }.defaultMessage", Matchers.is("must not be blank"))
                    .body("errors.find { it.field == 'name' }.defaultMessage", Matchers.is("must not be blank"));
        // @formatter:on
    }

    @Test
    void updateNotFoundContact(@DocumentSpecification final RequestSpecification documentSpecification) {
        ContactRequest request = contactRequestFactory.build();

        // @formatter:off
        RestAssured
                .given(documentSpecification)
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .filter(RestAssuredRestDocumentation.document(
                            "{ClassName}/{methodName}",
                            PayloadDocumentation.responseFields(CommonFieldDescriptor.notFound())
                    ))
                .when()
                    .put("/contacts/{id}", 1L)
                .then()
                    .log().all()
                    .spec(CommonResponseSpecification.notFound());
        // @formatter:on
    }
}

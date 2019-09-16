package br.com.leonardoferreira.poc.restdocs.integration.contact;

import br.com.leonardoferreira.poc.restdocs.ConstrainedFields;
import br.com.leonardoferreira.poc.restdocs.domain.Contact;
import br.com.leonardoferreira.poc.restdocs.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.restdocs.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.restdocs.extension.DocumentSpecification;
import br.com.leonardoferreira.poc.restdocs.extension.DocumentSpecificationExtension;
import br.com.leonardoferreira.poc.restdocs.factory.ContactRequestFactory;
import br.com.leonardoferreira.poc.restdocs.repository.ContactRepository;
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
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;

@ExtendWith(DocumentSpecificationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateContactIntegrationTest {

    private final ContactRepository contactRepository;

    private final ContactRequestFactory contactRequestFactory;

    @Autowired
    CreateContactIntegrationTest(final ContactRepository contactRepository,
                                 final ContactRequestFactory contactRequestFactory) {
        this.contactRepository = contactRepository;
        this.contactRequestFactory = contactRequestFactory;
    }

    @Test
    void withSuccess(@DocumentSpecification final RequestSpecification documentSpecification) {
        ConstrainedFields fields = new ConstrainedFields(ContactRequest.class);
        ContactRequest request = contactRequestFactory.build();

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
                            HeaderDocumentation.responseHeaders(
                                    HeaderDocumentation.headerWithName("location").description("Location to retrieve the created contact")
                            )))
                .when()
                    .post("/contacts")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .header("location", Matchers.containsString("/contacts/1"));
        // @formatter:on

        Contact contact = contactRepository.findById(1L)
                .orElseThrow(ResourceNotFoundException::new);

        Assertions.assertAll("Contact content",
                () -> Assertions.assertEquals(request.getName(), contact.getName()),
                () -> Assertions.assertEquals(request.getEmail(), contact.getEmail()));
    }

    @Test
    void failInValidations(@DocumentSpecification final RequestSpecification documentSpecification) {
        // @formatter:off
        RestAssured
                .given(documentSpecification)
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(new ContactRequest())
                    .filter(RestAssuredRestDocumentation.document("{ClassName}/{methodName}"))
                .when()
                    .post("/contacts")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("errors.find { it.field == 'email' }.defaultMessage", Matchers.is("must not be blank"))
                    .body("errors.find { it.field == 'name' }.defaultMessage", Matchers.is("must not be blank"));
        // @formatter:on
    }
}

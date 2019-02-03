package br.com.leonardoferreira.poc.restdocs.integration.contact;

import br.com.leonardoferreira.poc.restdocs.ConstrainedFields;
import br.com.leonardoferreira.poc.restdocs.domain.Contact;
import br.com.leonardoferreira.poc.restdocs.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.restdocs.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.restdocs.factory.ContactFactory;
import br.com.leonardoferreira.poc.restdocs.factory.ContactRequestFactory;
import br.com.leonardoferreira.poc.restdocs.integration.BaseIntegrationTest;
import br.com.leonardoferreira.poc.restdocs.repository.ContactRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
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
public class UpdateContactIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private ContactRequestFactory contactRequestFactory;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void updateContact() {
        contactFactory.create();
        ContactRequest request = contactRequestFactory.build();

        ConstrainedFields fields = new ConstrainedFields(ContactRequest.class);

        // @formatter:off
        RestAssured
                .given(super.documentSpecification)
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
    public void failInValidations() {
        contactFactory.create();

        // @formatter:off
        RestAssured
                .given(super.documentSpecification)
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
    public void updateNotFoundContact() {
        ContactRequest request = contactRequestFactory.build();

        // @formatter:off
        RestAssured
                .given(super.documentSpecification)
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .filter(RestAssuredRestDocumentation.document("{ClassName}/{methodName}",
                            PayloadDocumentation.responseFields(notFoundFields())))
                .when()
                    .put("/contacts/{id}", 1L)
                .then()
                    .log().all()
                    .spec(notFoundSpec());
        // @formatter:on
    }
}

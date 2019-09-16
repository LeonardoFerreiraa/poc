package br.com.leonardoferreira.poc.restdocs.integration.contact;

import br.com.leonardoferreira.poc.restdocs.domain.Contact;
import br.com.leonardoferreira.poc.restdocs.extension.DocumentSpecification;
import br.com.leonardoferreira.poc.restdocs.extension.DocumentSpecificationExtension;
import br.com.leonardoferreira.poc.restdocs.factory.ContactFactory;
import br.com.leonardoferreira.poc.restdocs.fielddescriptor.CommonFieldDescriptor;
import br.com.leonardoferreira.poc.restdocs.matcher.IdMatcher;
import br.com.leonardoferreira.poc.restdocs.specification.CommonResponseSpecification;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.time.format.DateTimeFormatter;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;

@ExtendWith(DocumentSpecificationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchContactIntegrationTest {

    private final ContactFactory contactFactory;

    @Autowired
    SearchContactIntegrationTest(final ContactFactory contactFactory) {
        this.contactFactory = contactFactory;
    }

    @Test
    void findAll(@DocumentSpecification final RequestSpecification documentSpecification) {
        contactFactory.create(5);

        // @formatter:off
        RestAssured
                .given(documentSpecification)
                    .log().all()
                    .filter(RestAssuredRestDocumentation.document("{ClassName}/{methodName}",
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.fieldWithPath("[]").description("List of contacts"),
                                    PayloadDocumentation.fieldWithPath("[].id").description("Contact unique identifier"),
                                    PayloadDocumentation.fieldWithPath("[].name").description("Contact name"),
                                    PayloadDocumentation.fieldWithPath("[].email").description("Contact email"),
                                    PayloadDocumentation.fieldWithPath("[].createdAt").description("Contact creation date"),
                                    PayloadDocumentation.fieldWithPath("[].updatedAt").description("Contact last update date")
                            )))
                .when()
                    .get("/contacts")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .body("$", Matchers.hasSize(5))
                    .body("[0].id", Matchers.notNullValue())
                    .body("[0].name", Matchers.notNullValue())
                    .body("[0].email", Matchers.notNullValue())
                    .body("[0].createdAt", Matchers.notNullValue())
                    .body("[0].updatedAt", Matchers.notNullValue());
        // @formatter:on
    }

    @Test
    void findContactById(@DocumentSpecification final RequestSpecification documentSpecification) {
        Contact contact = contactFactory.create();

        // @formatter:off
        RestAssured
                .given(documentSpecification)
                    .log().all()
                    .filter(RestAssuredRestDocumentation.document("{ClassName}/{methodName}",
                            RequestDocumentation.pathParameters(
                                    RequestDocumentation.parameterWithName("id").description("Contact unique identifier")),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.fieldWithPath("id").description("Contact unique identifier"),
                                    PayloadDocumentation.fieldWithPath("name").description("Contact name"),
                                    PayloadDocumentation.fieldWithPath("email").description("Contact email"),
                                    PayloadDocumentation.fieldWithPath("createdAt").description("Contact creation date"),
                                    PayloadDocumentation.fieldWithPath("updatedAt").description("Contact last update date"))))
                .when()
                    .get("/contacts/{id}", 1L)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .body("id", IdMatcher.is(contact.getId()))
                    .body("name", Matchers.is(contact.getName()))
                    .body("email", Matchers.is(contact.getEmail()))
                    .body("createdAt", Matchers.is(contact.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))))
                    .body("updatedAt", Matchers.is(contact.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        // @formatter:on
    }

    @Test
    void findByIdNotFound(@DocumentSpecification final RequestSpecification documentSpecification) {
        // @formatter:off
        RestAssured
                .given(documentSpecification)
                    .log().all()
                    .filter(RestAssuredRestDocumentation.document("{ClassName}/{methodName}",
                            PayloadDocumentation.responseFields(CommonFieldDescriptor.notFound()),
                            RequestDocumentation.pathParameters(
                                    RequestDocumentation.parameterWithName("id").description("Contact unique identifier"))))
                .when()
                    .get("/contacts/{id}", 1L)
                .then()
                    .log().all()
                    .spec(CommonResponseSpecification.notFound());
        // @formatter:on
    }

}


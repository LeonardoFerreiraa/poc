package br.com.leonardoferreira.poc.restdocs.integration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;

public class BaseIntegrationTest {

    protected RequestSpecification documentSpecification;
    @LocalServerPort
    private Integer port;
    @Autowired
    private CleanDatabase cleanDatabase;

    @BeforeEach
    public void beforeEach(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;

        cleanDatabase.clean();

        this.documentSpecification = new RequestSpecBuilder()
                .addFilter(RestAssuredRestDocumentation.documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                Preprocessors.prettyPrint(),
                                Preprocessors.modifyUris().scheme("http").host("poc-spring-rest-docs").removePort())
                        .withResponseDefaults(
                                Preprocessors.prettyPrint(),
                                Preprocessors.modifyUris().scheme("http").host("poc-spring-rest-docs").removePort())
                ).build();
    }

    protected ResponseSpecification notFoundSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .expectBody("timestamp", Matchers.notNullValue())
                .expectBody("error", Matchers.is("Not Found"))
                .expectBody("message", Matchers.is("Resource Not Found"))
                .expectBody("status", Matchers.is(404))
                .build();
    }

    protected FieldDescriptor[] notFoundFields() {
        return new FieldDescriptor[]{
                PayloadDocumentation.fieldWithPath("timestamp").description("Error timestamp"),
                PayloadDocumentation.fieldWithPath("status").description("HTTP status"),
                PayloadDocumentation.fieldWithPath("error").description("HTTP status description"),
                PayloadDocumentation.fieldWithPath("message").description("Error description"),
                PayloadDocumentation.fieldWithPath("path").description("Requested path")
        };
    }
}

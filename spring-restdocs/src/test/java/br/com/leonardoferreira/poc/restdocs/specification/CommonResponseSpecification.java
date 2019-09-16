package br.com.leonardoferreira.poc.restdocs.specification;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonResponseSpecification {

    public static ResponseSpecification notFound() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .expectBody("timestamp", Matchers.notNullValue())
                .expectBody("error", Matchers.is("Not Found"))
                .expectBody("message", Matchers.is("Resource Not Found"))
                .expectBody("status", Matchers.is(404))
                .build();
    }

}

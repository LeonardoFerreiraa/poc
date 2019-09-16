package br.com.leonardoferreira.poc.restdocs.extension;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;

public class DocumentSpecificationExtension extends RestDocumentationExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == RequestSpecification.class &&
                parameterContext.getParameter().isAnnotationPresent(DocumentSpecification.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
        final RestDocumentationContextProvider restDocumentation = (RestDocumentationContextProvider) super.resolveParameter(parameterContext, extensionContext);

        return new RequestSpecBuilder()
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

}

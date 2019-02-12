package br.com.leonardoferreira.poc.restdocs;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.util.StringUtils;

public class ConstrainedFields {

    private final ConstraintDescriptions constraintDescriptions;

    public ConstrainedFields(Class<?> input) {
        this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    public FieldDescriptor withPath(String path) {
        return PayloadDocumentation.fieldWithPath(path).attributes(
                Attributes.key("constraints")
                        .value(StringUtils.collectionToDelimitedString(
                                this.constraintDescriptions.descriptionsForProperty(path), ". ")));
    }
}
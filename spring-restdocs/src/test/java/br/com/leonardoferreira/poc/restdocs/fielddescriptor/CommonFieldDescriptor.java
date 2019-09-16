package br.com.leonardoferreira.poc.restdocs.fielddescriptor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonFieldDescriptor {

    public static FieldDescriptor[] notFound() {
        return new FieldDescriptor[]{
                PayloadDocumentation.fieldWithPath("timestamp").description("Error timestamp"),
                PayloadDocumentation.fieldWithPath("status").description("HTTP status"),
                PayloadDocumentation.fieldWithPath("error").description("HTTP status description"),
                PayloadDocumentation.fieldWithPath("message").description("Error description"),
                PayloadDocumentation.fieldWithPath("path").description("Requested path")
        };
    }

}

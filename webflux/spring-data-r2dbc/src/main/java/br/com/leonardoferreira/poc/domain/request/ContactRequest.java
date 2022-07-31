package br.com.leonardoferreira.poc.domain.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

}

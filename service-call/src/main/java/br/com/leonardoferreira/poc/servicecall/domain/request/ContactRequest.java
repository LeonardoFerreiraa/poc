package br.com.leonardoferreira.poc.servicecall.domain.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class ContactRequest {

    @NotEmpty
    private String name;

    @Email
    private String email;

}

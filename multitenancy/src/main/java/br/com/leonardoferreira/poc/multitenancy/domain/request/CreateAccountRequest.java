package br.com.leonardoferreira.poc.multitenancy.domain.request;

import lombok.Data;

@Data
public class CreateAccountRequest {

    private String username;

    private String password;

}

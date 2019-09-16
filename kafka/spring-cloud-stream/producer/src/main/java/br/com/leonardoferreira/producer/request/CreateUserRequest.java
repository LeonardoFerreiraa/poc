package br.com.leonardoferreira.producer.request;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String name;

    private String email;

}

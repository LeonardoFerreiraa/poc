package br.com.leonardoferreira.poc.servicecall.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class ContactResponse {

    private Long id;

    private String fullName;

    private String email;

    private String createdAt;

    private String updatedAt;

    private List<PhoneResponse> phones;
}

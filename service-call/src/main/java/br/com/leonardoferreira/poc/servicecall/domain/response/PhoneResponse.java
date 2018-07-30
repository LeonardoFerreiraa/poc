package br.com.leonardoferreira.poc.servicecall.domain.response;

import lombok.Data;

@Data
public class PhoneResponse {

    private Long id;

    private String number;

    private ContactResponse contact;

}

package br.com.leonardoferreira.poc.service;

import java.util.List;

import br.com.leonardoferreira.poc.domain.response.ContactResponse;

public interface ContactService {

    List<ContactResponse> findAll();

}

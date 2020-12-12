package br.com.leonardoferreira.poc.controller;

import br.com.leonardoferreira.poc.domain.response.ContactResponse;
import br.com.leonardoferreira.poc.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public List<ContactResponse> findAll() {
        return contactService.findAll();
    }

}

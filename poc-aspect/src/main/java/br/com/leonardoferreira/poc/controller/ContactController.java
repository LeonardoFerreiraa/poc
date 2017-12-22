package br.com.leonardoferreira.poc.controller;

import br.com.leonardoferreira.poc.domain.Contact;
import br.com.leonardoferreira.poc.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public List<Contact> findAll(@ModelAttribute Contact contact) {
        return contactService.findAll(contact);
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody Contact contact) {
        contactService.create(contact);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}

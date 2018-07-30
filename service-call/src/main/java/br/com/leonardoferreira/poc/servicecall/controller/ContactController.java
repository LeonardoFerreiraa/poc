package br.com.leonardoferreira.poc.servicecall.controller;

import br.com.leonardoferreira.poc.servicecall.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.servicecall.domain.response.ContactResponse;
import br.com.leonardoferreira.poc.servicecall.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public List<ContactResponse> findAll() {
        return contactService.findAll();
    }

    @GetMapping("/{id}")
    public ContactResponse findById(@PathVariable Long id) {
        return contactService.findById(id);
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody ContactRequest contactRequest) {
        Long id = contactService.create(contactRequest);
        return ResponseEntity.created(URI.create("/contacts/" + id)).build();
    }

}

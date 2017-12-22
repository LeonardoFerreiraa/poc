package br.com.leonardoferreira.hello.controller;

import br.com.leonardoferreira.hello.domain.Contact;
import br.com.leonardoferreira.hello.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by lferreira on 6/30/17.
 */
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public HttpEntity<List<Contact>> index() {
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<Contact> get(@PathVariable final Long id) {
        final Contact contact = contactService.findOne(id);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contact);
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody @Valid final Contact contact) {
        contactService.save(contact);
        return ResponseEntity.created(URI.create("/api/v1/contacts/" + contact.getId())).build();
    }

    @PutMapping
    public HttpEntity<?> update(@RequestBody @Valid final Contact contact) {
        contactService.save(contact);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public HttpEntity destroy(@PathVariable final Long id) {
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

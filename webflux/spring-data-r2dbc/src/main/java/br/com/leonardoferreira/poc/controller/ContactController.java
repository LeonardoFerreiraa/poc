package br.com.leonardoferreira.poc.controller;

import br.com.leonardoferreira.poc.domain.Contact;
import br.com.leonardoferreira.poc.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.service.ContactService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public Flux<Contact> findAll() {
        return contactService.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Contact> findAll(@RequestParam(defaultValue = "5", required = false) int pageSize) {
        return contactService.findAll(pageSize);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Contact>> findById(@PathVariable Long id) {
        return contactService.findById(id)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResourceNotFoundException.class, ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<HttpEntity<?>> create(@RequestBody @Valid ContactRequest contactRequest,
                                      UriComponentsBuilder uriBuilder) {
        return contactService.insert(contactRequest)
                .map(id -> ResponseEntity
                        .created(uriBuilder.path("/contacts/{id}").build(id))
                        .build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> update(@PathVariable Long id,
                                               @RequestBody @Valid ContactRequest contactRequest) {
        return contactService.update(id, contactRequest)
                .thenReturn(ResponseEntity.noContent().build())
                .onErrorReturn(ResourceNotFoundException.class, ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable Long id) {
        return contactService.delete(id)
                .thenReturn(ResponseEntity.noContent().build())
                .onErrorReturn(ResourceNotFoundException.class, ResponseEntity.notFound().build());
    }


}

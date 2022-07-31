package br.com.leonardoferreira.poc.controller;

import br.com.leonardoferreira.poc.domain.entity.CustomerEntity;
import br.com.leonardoferreira.poc.domain.request.CustomerRequest;
import br.com.leonardoferreira.poc.domain.response.CustomerResponse;
import br.com.leonardoferreira.poc.domain.response.UpdateCustomerRequest;
import br.com.leonardoferreira.poc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Mono<CustomerResponse> findById(@PathVariable final String id) {
        return customerService.findById(id);
    }

    @GetMapping
    public Flux<CustomerResponse> findAll() {
        return customerService.findAll();
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> create(@RequestBody final CustomerRequest request,
                                             final UriComponentsBuilder uriComponentsBuilder) {
        return customerService.create(request)
                .map(id ->
                        ResponseEntity.created(
                                uriComponentsBuilder.path("/customers/{id}")
                                        .build(id)
                        ).build()
                );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable final String id,
                             @RequestBody final UpdateCustomerRequest request) {
        return customerService.update(id, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable final String id) {
        return customerService.delete(id);
    }

}

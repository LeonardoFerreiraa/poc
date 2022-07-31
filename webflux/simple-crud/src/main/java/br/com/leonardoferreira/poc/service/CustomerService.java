package br.com.leonardoferreira.poc.service;

import br.com.leonardoferreira.poc.domain.entity.CustomerEntity;
import br.com.leonardoferreira.poc.domain.request.CustomerRequest;
import br.com.leonardoferreira.poc.domain.response.CustomerResponse;
import br.com.leonardoferreira.poc.domain.response.UpdateCustomerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<CustomerResponse> findById(String id);

    Flux<CustomerResponse> findAll();

    Mono<String> create(CustomerRequest request);

    Mono<Void> update(String id, UpdateCustomerRequest request);

    Mono<Void> delete(String id);

}

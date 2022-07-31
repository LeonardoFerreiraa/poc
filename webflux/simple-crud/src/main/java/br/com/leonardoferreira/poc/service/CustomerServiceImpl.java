package br.com.leonardoferreira.poc.service;

import br.com.leonardoferreira.poc.domain.entity.CustomerEntity;
import br.com.leonardoferreira.poc.domain.exception.CustomerNotFoundException;
import br.com.leonardoferreira.poc.domain.request.CustomerRequest;
import br.com.leonardoferreira.poc.domain.response.CustomerResponse;
import br.com.leonardoferreira.poc.domain.response.UpdateCustomerRequest;
import br.com.leonardoferreira.poc.mapper.CustomerMapper;
import br.com.leonardoferreira.poc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public Mono<CustomerResponse> findById(final String id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(id)))
                .map(customerMapper::customerToCustomerResponse);
    }

    @Override
    public Flux<CustomerResponse> findAll() {
        return customerRepository.findAll()
                .map(customerMapper::customerToCustomerResponse);
    }

    @Override
    public Mono<String> create(final CustomerRequest request) {
        final CustomerEntity customerEntity = CustomerEntity.builder()
                .name(request.getName())
                .build();

        return customerRepository.save(customerEntity)
                .map(CustomerEntity::getId);
    }

    @Override
    public Mono<Void> update(final String id, final UpdateCustomerRequest request) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(id)))
                .map(customer ->
                        CustomerEntity.builder()
                                .id(customer.getId())
                                .name(request.getName())
                                .createdAt(customer.getCreatedAt())
                                .updatedAt(customer.getUpdatedAt())
                                .build()
                )
                .flatMap(customerRepository::save)
                .then();
    }

    @Override
    public Mono<Void> delete(final String id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(id)))
                .flatMap(customerRepository::delete);
    }
}

package br.com.leonardoferreira.poc.mapper;

import java.time.format.DateTimeFormatter;

import br.com.leonardoferreira.poc.domain.entity.CustomerEntity;
import br.com.leonardoferreira.poc.domain.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public CustomerResponse customerToCustomerResponse(final CustomerEntity customerEntity) {
        return CustomerResponse.builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .createdAt(dateTimeFormatter.format(customerEntity.getCreatedAt()))
                .updatedAt(dateTimeFormatter.format(customerEntity.getUpdatedAt()))
                .build();
    }

}

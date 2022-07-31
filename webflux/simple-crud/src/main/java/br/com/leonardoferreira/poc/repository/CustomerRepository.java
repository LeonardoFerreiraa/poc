package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerEntity, String> {
}

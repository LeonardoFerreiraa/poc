package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.Contact;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ContactRepository extends ReactiveCrudRepository<Contact, Long> {

    @Query("SELECT * FROM contact LIMIT $1 OFFSET $2")
    Flux<Contact> findAll(int limit, int offset);

}

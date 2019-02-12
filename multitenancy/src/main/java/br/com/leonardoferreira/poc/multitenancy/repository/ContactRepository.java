package br.com.leonardoferreira.poc.multitenancy.repository;

import br.com.leonardoferreira.poc.multitenancy.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}

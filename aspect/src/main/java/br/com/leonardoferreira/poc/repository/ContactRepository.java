package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {
    List<Contact> findByName(String name);
}

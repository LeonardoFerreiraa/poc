package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {
    List<Contact> findByName(String name);
}

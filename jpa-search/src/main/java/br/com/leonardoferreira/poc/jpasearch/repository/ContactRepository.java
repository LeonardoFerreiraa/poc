package br.com.leonardoferreira.poc.jpasearch.repository;

import br.com.leonardoferreira.poc.jpasearch.domain.Contact;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

    List<Contact> findAll(Example<Contact> example);

    List<Contact> findAll(Specification<Contact> specification);

}

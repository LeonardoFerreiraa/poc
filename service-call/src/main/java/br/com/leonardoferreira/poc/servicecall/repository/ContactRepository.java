package br.com.leonardoferreira.poc.servicecall.repository;

import br.com.leonardoferreira.poc.servicecall.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}

package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}

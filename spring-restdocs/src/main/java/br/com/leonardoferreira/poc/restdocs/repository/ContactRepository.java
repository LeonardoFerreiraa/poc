package br.com.leonardoferreira.poc.restdocs.repository;

import br.com.leonardoferreira.poc.restdocs.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}

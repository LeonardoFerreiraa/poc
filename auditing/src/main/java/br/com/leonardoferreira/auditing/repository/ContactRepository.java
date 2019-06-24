package br.com.leonardoferreira.auditing.repository;

import br.com.leonardoferreira.auditing.domain.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}

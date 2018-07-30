package br.com.leonardoferreira.poc.servicecall.repository;

import br.com.leonardoferreira.poc.servicecall.domain.Contact;
import br.com.leonardoferreira.poc.servicecall.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

    boolean existsByNumberAndContact(String number, Contact contact);
}

package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}

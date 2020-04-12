package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.SimpleMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleMessageRepository extends JpaRepository<SimpleMessage, String> {
}

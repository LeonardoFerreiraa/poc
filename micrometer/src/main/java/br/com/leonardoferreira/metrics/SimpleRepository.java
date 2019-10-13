package br.com.leonardoferreira.metrics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRepository extends CrudRepository<SimpleMessage, String> {
}

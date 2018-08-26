package br.com.leonardoferreira.poc.statemachine.repository;

import br.com.leonardoferreira.poc.statemachine.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}

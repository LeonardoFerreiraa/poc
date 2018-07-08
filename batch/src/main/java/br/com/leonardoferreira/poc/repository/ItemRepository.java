package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}

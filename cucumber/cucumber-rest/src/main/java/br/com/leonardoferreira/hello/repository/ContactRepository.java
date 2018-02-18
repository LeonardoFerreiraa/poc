package br.com.leonardoferreira.hello.repository;

import br.com.leonardoferreira.hello.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lferreira on 6/30/17.
 */
@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}

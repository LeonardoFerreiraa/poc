package br.com.leonardoferreira.poc.restspringsecurity.repository;

import br.com.leonardoferreira.poc.restspringsecurity.domain.Account;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

}

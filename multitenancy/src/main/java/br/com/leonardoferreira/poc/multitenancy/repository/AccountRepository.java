package br.com.leonardoferreira.poc.multitenancy.repository;

import br.com.leonardoferreira.poc.multitenancy.domain.Account;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

}

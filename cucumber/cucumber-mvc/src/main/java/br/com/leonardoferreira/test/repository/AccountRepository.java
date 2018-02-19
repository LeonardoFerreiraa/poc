package br.com.leonardoferreira.test.repository;

import br.com.leonardoferreira.test.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lferreira on 2/18/18
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByUsername(String username);
}

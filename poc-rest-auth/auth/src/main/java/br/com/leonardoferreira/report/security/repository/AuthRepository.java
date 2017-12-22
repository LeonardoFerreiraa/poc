package br.com.leonardoferreira.report.security.repository;

import br.com.leonardoferreira.report.security.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AuthRepository extends CrudRepository<Account, Long> {
    Account findByUsername(String username);

    Account findByAccessToken(String authorization);
}

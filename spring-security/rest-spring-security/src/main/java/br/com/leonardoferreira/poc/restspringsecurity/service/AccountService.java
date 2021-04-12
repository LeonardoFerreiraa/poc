package br.com.leonardoferreira.poc.restspringsecurity.service;

import br.com.leonardoferreira.poc.restspringsecurity.domain.Account;
import br.com.leonardoferreira.poc.restspringsecurity.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.restspringsecurity.repository.AccountRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findByUsername(final String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
    }

}

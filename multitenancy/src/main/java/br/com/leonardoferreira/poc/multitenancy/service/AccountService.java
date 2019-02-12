package br.com.leonardoferreira.poc.multitenancy.service;

import br.com.leonardoferreira.poc.multitenancy.domain.Account;
import br.com.leonardoferreira.poc.multitenancy.domain.request.CreateAccountRequest;
import br.com.leonardoferreira.poc.multitenancy.mapper.AccountMapper;
import br.com.leonardoferreira.poc.multitenancy.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Method=loadUserByUsername, username={}", username);

        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    @Transactional
    public Long create(CreateAccountRequest createAccountRequest) {
        log.info("Method=create, createAccountRequest={}", createAccountRequest);

        Account account = accountMapper.accountFromCreateAccountRequest(createAccountRequest);
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        accountRepository.save(account);

        return account.getId();
    }
}

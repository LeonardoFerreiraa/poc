package br.com.leonardoferreira.report.security.service;

import br.com.leonardoferreira.report.security.domain.Account;
import br.com.leonardoferreira.report.security.repository.AuthRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public boolean login(Account account) {
        Account dbAcc = authRepository.findByUsername(account.getUsername());
        if (dbAcc == null) {
            return false;
        }

        if (!passwordEncoder.matches(account.getPassword(), dbAcc.getPassword())) {
            return false;
        }

        dbAcc.setAccessToken(UUID.randomUUID().toString());
        authRepository.save(dbAcc);

        BeanUtils.copyProperties(dbAcc, account);

        return true;
    }

    public Account isValidToken(String authorization) {
        if (authorization == null) {
            return null;
        }

        return authRepository.findByAccessToken(authorization);
    }
}

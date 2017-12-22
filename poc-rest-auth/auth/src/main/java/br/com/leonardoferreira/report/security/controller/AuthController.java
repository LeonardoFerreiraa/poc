package br.com.leonardoferreira.report.security.controller;

import br.com.leonardoferreira.report.security.domain.Account;
import br.com.leonardoferreira.report.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService accountService;

    @PostMapping("/login")
    public HttpEntity<Account> login(@RequestBody Account account) {
        if (accountService.login(account)) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

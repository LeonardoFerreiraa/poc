package br.com.leonardoferreira.poc.restspringsecurity.controller;

import br.com.leonardoferreira.poc.restspringsecurity.domain.Account;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/me")
    public Account principal(@AuthenticationPrincipal Account account) {
        return account;
    }

}

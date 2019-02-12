package br.com.leonardoferreira.poc.multitenancy.controller;

import br.com.leonardoferreira.poc.multitenancy.domain.request.CreateAccountRequest;
import br.com.leonardoferreira.poc.multitenancy.service.AccountService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateAccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAccountRequest createAccountRequest) {
        Long id = accountService.create(createAccountRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/accounts/{id}")
                .build(id);

        return ResponseEntity.created(location).build();
    }

}

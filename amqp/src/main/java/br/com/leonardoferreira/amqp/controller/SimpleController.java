package br.com.leonardoferreira.amqp.controller;

import br.com.leonardoferreira.amqp.domain.SimpleMessage;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class SimpleController {

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SimpleMessage simpleMessage) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

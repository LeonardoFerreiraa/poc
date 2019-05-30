package br.com.leonardoferreira.poc.kafka.controller;

import br.com.leonardoferreira.poc.kafka.domain.SimpleMessage;
import javax.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class SimpleController {

    private final KafkaTemplate<Object, Object> template;

    public SimpleController(KafkaTemplate<Object, Object> template) {
        this.template = template;
    }

    @PostMapping
    public HttpEntity<?> create(
            @Valid @RequestBody SimpleMessage simpleMessage
    ) {
        template.send("simple.topic", simpleMessage);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}

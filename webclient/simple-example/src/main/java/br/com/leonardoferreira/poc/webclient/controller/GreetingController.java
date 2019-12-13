package br.com.leonardoferreira.poc.webclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/greetings")
public class GreetingController {

    @GetMapping
    public Mono<String> greet() {
        return Mono.just("Hello world");
    }

}

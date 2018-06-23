package br.com.leonardoferreira.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String helloWorld() {
        return "hello world";
    }
}

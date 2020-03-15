package br.com.leonardoferreira.poc.feign.controller;

import java.util.List;

import br.com.leonardoferreira.poc.feign.client.TodoClient;
import br.com.leonardoferreira.poc.feign.domain.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/todos")
public class TodoRunner {

    private final TodoClient todoClient;

    public TodoRunner(TodoClient todoClient) {
        this.todoClient = todoClient;
    }

    @GetMapping
    public List<Todo> findAll() {
        return todoClient.findByUserId(1L);
    }

    @GetMapping("/{id}")
    public Todo findById(@PathVariable final Long id) {
        return todoClient.findById(id);
    }
}

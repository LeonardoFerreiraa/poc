package br.com.leonardoferreira.poc.feign.runner;

import br.com.leonardoferreira.poc.feign.client.TodoClient;
import br.com.leonardoferreira.poc.feign.domain.Todo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TodoRunner implements CommandLineRunner {

    private final TodoClient todoClient;

    public TodoRunner(TodoClient todoClient) {
        this.todoClient = todoClient;
    }

    @Override
    public void run(String... args) throws Exception {
        Todo todo = todoClient.findById(1L);
        log.info("todo={}", todo);

        List<Todo> todos = todoClient.findByUserId(1L);
        log.info("todos={}", todos);
    }
}

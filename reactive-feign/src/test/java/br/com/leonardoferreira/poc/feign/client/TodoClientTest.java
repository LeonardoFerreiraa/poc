package br.com.leonardoferreira.poc.feign.client;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.Disposable;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoClientTest {

    @Autowired
    private TodoClient todoClient;

    @Test
    @SneakyThrows
    void retrieveAll() {
        Disposable firstFind = todoClient.findAll()
                .subscribe(todos -> {
                    todos.forEach(todo -> log.info("primeiro -> {}", todo));
                });
        Disposable secondFind = todoClient.findAll()
                .subscribe(todos -> {
                    todos.forEach(todo -> log.info("segundo -> {}", todo));
                });

        do {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } while (!firstFind.isDisposed() && !secondFind.isDisposed());
    }
}

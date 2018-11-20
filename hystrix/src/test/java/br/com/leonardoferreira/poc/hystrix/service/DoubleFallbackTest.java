package br.com.leonardoferreira.poc.hystrix.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Exemplo com dois fallbaks")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoubleFallbackTest {

    @Autowired
    private DoubleFallback doubleFallback;

    @Test
    @DisplayName("Retorna o resultado do segundo fallback")
    void returnsSecondFallbackResult() {
        String operation = doubleFallback.operation();
        Assertions.assertEquals("fallback2 - success", operation);
    }
}

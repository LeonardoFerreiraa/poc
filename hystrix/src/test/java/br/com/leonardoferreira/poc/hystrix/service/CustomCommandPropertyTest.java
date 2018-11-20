package br.com.leonardoferreira.poc.hystrix.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Exemplo de property customizada para o comando")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomCommandPropertyTest {

    @Autowired
    private CustomCommandProperty customCommandProperty;

    @Test
    @DisplayName("Timeout customizado")
    void customTimeout() {
        String operation = customCommandProperty.operation();
        Assertions.assertEquals("fallback", operation);
    }

}

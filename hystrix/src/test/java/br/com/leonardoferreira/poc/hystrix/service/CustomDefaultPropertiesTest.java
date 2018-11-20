package br.com.leonardoferreira.poc.hystrix.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Exemplo de configurações default para todos os métodos da classe")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomDefaultPropertiesTest {

    @Autowired
    private CustomDefaultProperties customDefaultProperties;

    @Test
    @DisplayName("Chamara o método de fallback")
    void callFallback() {
        String operation = customDefaultProperties.operation();
        Assertions.assertEquals("fallback", operation);
    }
}

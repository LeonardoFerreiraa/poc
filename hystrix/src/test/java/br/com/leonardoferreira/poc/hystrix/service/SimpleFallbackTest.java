package br.com.leonardoferreira.poc.hystrix.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Exemplo de fallback simples")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleFallbackTest {

    @Autowired
    private SimpleFallback simpleFallback;

    @Test
    @DisplayName("Retorna saudações quando invocado com parametro valido")
    void returnsGreetingWithWhenNotNullParameter() {
        String foo = simpleFallback.operation("Foo");
        Assertions.assertEquals("Hello Foo", foo);
    }

    @Test
    @DisplayName("Retorna o fallback quando é invocado com parametro nullo")
    void returnsFallbackWhenNullName() {
        String operation = simpleFallback.operation(null);
        Assertions.assertEquals("Hello world", operation);
    }

}

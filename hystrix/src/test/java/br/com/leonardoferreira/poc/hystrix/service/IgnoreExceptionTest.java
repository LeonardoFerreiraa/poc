package br.com.leonardoferreira.poc.hystrix.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Exemplo de configuração de comando ignorando alguma exception")
public class IgnoreExceptionTest {

    @Autowired
    private IgnoreException ignoreException;

    @Test
    @DisplayName("Não chama o fallback quando retorna RuntimeException")
    void ignoreRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, () ->
                ignoreException.operation());
    }
}

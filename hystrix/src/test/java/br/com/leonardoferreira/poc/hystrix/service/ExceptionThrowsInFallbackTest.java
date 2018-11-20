package br.com.leonardoferreira.poc.hystrix.service;

import br.com.leonardoferreira.poc.hystrix.exception.FallbackException;
import br.com.leonardoferreira.poc.hystrix.exception.OperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Exemplo de quando uma excessão é lançada no fallback")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExceptionThrowsInFallbackTest {

    @Autowired
    private ExceptionThrowsInFallback exceptionThrowsInFallback;

    @Test
    @DisplayName("Retorna a excessão lançada pelo fallback")
    void name() {
        Assertions.assertThrows(FallbackException.class, () ->
                exceptionThrowsInFallback.operation());
    }
}

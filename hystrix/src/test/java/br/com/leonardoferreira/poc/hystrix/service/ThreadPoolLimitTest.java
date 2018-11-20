package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import java.util.concurrent.RejectedExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Exemplos com thread-pool")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThreadPoolLimitTest {

    @Autowired
    private ThreadPoolLimit threadPoolLimit;

    @Test
    @DisplayName("Rejeita requisições excedentes")
    void rejectsSurplusRequests() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> threadPoolLimit.slowOperation()).start();
        }

        Thread.sleep(1000);

        HystrixRuntimeException hystrixRuntimeException = Assertions.assertThrows(HystrixRuntimeException.class,
                () -> threadPoolLimit.slowOperation());
        Assertions.assertTrue(hystrixRuntimeException.getCause() instanceof RejectedExecutionException);
    }

    @Test
    @DisplayName("Alterando tamanho do thread pool")
    void changeThreadPool() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> threadPoolLimit.slowOperationWithBigThreadPool()).start();
        }

        Thread.sleep(1000);

        threadPoolLimit.slowOperationWithBigThreadPool();
    }
}

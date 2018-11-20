package br.com.leonardoferreira.poc.hystrix.service;

import br.com.leonardoferreira.poc.hystrix.exception.FallbackException;
import br.com.leonardoferreira.poc.hystrix.exception.OperationException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExceptionThrowsInFallback {

    @HystrixCommand(fallbackMethod = "fallback")
    public String operation() {
        log.info("Info=invocando operation");

        throw new OperationException();
    }

    private String fallback() {
        log.info("Info=invocando fallback");
        throw new FallbackException();
    }
}

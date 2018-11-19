package br.com.leonardoferreira.poc.hystrix.service;

import br.com.leonardoferreira.poc.hystrix.exception.FallbackException;
import br.com.leonardoferreira.poc.hystrix.exception.OperationException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class ExceptionThrowInFallback {

    @HystrixCommand(fallbackMethod = "fallback")
    public String operation() {
        throw new OperationException();
    }

    private String fallback(Throwable e) {
        throw new FallbackException();
    }
}

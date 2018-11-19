package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class IgnoreException {
    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = RuntimeException.class)
    public String operation() {
        throw new RuntimeException();
    }

    private String fallback() {
        return ":/";
    }
}

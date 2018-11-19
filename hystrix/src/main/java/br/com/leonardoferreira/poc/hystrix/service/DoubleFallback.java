package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class DoubleFallback {

    @HystrixCommand(fallbackMethod = "fallback")
    public String operation() {
        throw new RuntimeException();
    }

    @HystrixCommand(fallbackMethod = "fallback2")
    private String fallback() {
        throw new RuntimeException();
    }

    private String fallback2() {
        return "fallback2 - success";
    }

}

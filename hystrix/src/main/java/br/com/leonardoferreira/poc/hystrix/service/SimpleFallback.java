package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class SimpleFallback {

    @HystrixCommand(fallbackMethod = "fallback")
    public String operation(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        return "Hello " + name;
    }

    private String fallback(String name, Throwable throwable) {
        return "Hello world";
    }

}

package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
@DefaultProperties(defaultFallback = "fallback")
public class CustomDefaultProperties {

    @HystrixCommand
    public String operation() {
        throw new RuntimeException();
    }

    private String fallback() {
        return "fallback";
    }
}

package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
@DefaultProperties(defaultFallback = "fallback")
public class TestService6 {

    @HystrixCommand
    public String operation() {
        throw new RuntimeException();
    }

    public String fallback() {
        return "fallback";
    }
}

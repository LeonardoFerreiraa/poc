package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleFallback {

    @HystrixCommand(fallbackMethod = "fallback")
    public String operation(String name) {
        log.info("Info=invocando operation, name={}", name);

        if (name == null) {
            throw new IllegalArgumentException();
        }

        return "Hello " + name;
    }

    private String fallback(String name) {
        log.info("Info=invocando callback, name={}", name);

        return "Hello world";
    }

}

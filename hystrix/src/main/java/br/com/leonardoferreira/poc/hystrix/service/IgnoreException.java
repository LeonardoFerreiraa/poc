package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IgnoreException {

    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = RuntimeException.class)
    public String operation() {
        log.info("Info=invocando operation");

        throw new RuntimeException();
    }

    private String fallback() {
        log.info("Info=invocando callback");

        return ":/";
    }
}

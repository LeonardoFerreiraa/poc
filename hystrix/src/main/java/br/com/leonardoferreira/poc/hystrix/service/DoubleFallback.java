package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DoubleFallback {

    @HystrixCommand(fallbackMethod = "fallback")
    public String operation() {
        log.info("Info=invocando operation");

        throw new RuntimeException();
    }

    @HystrixCommand(fallbackMethod = "fallback2")
    private String fallback() {
        log.info("Info=invocando fallback");

        throw new RuntimeException();
    }

    private String fallback2() {
        log.info("Info=invocando fallback2");

        return "fallback2 - success";
    }

}

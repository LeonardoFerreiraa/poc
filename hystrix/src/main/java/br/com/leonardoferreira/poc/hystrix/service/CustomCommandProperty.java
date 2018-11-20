package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomCommandProperty {

    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public String operation() {
        log.info("Info=chamando operation");

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            log.error("Erro thread sleep", e);
        }

        return "ok";
    }

    private String fallback() {
        return "fallback";
    }
}

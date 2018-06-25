package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

/**
 * @author s2it_leferreira
 * @since 25/06/18 16:57
 */
@Service
public class TestService5 {

    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10")
    })
    public String operation(String param) {
        if (param == null) {
            throw new IllegalArgumentException();
        }
        return "ok";
    }

    private String fallback(String param) {
        return "fallback";
    }
}

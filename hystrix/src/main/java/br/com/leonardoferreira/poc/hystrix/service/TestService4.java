package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

/**
 * @author s2it_leferreira
 * @since 25/06/18 16:37
 */
@Service
public class TestService4 {
    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = RuntimeException.class)
    public String operation() {
        throw new RuntimeException();
    }

    private String fallback() {
        return ":/";
    }
}

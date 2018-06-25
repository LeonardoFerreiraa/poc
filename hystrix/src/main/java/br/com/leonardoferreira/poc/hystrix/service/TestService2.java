package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

/**
 * @author s2it_leferreira
 * @since 25/06/18 13:09
 */
@Service
public class TestService2 {

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

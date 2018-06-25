package br.com.leonardoferreira.poc.hystrix.service;

import br.com.leonardoferreira.poc.hystrix.exception.FallbackException;
import br.com.leonardoferreira.poc.hystrix.exception.OperationException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixException;
import org.springframework.stereotype.Service;

/**
 * @author s2it_leferreira
 * @since 25/06/18 13:13
 */
@Service
public class TestService3 {

    @HystrixCommand(fallbackMethod = "fallback")
    public String operation() {
        throw new OperationException();
    }

    private String fallback(Throwable e) {
        throw new FallbackException();
    }
}

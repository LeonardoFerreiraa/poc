package br.com.leonardoferreira.poc.hystrix.controller;

import br.com.leonardoferreira.poc.hystrix.exception.FallbackException;
import br.com.leonardoferreira.poc.hystrix.exception.OperationException;
import br.com.leonardoferreira.poc.hystrix.service.TestService1;
import br.com.leonardoferreira.poc.hystrix.service.TestService2;
import br.com.leonardoferreira.poc.hystrix.service.TestService3;
import br.com.leonardoferreira.poc.hystrix.service.TestService4;
import br.com.leonardoferreira.poc.hystrix.service.TestService5;
import br.com.leonardoferreira.poc.hystrix.service.TestService6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author s2it_leferreira
 * @since 25/06/18 10:50
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService1 testService1;

    @Autowired
    private TestService2 testService2;

    @Autowired
    private TestService3 testService3;

    @Autowired
    private TestService4 testService4;

    @Autowired
    private TestService5 testService5;

    @Autowired
    private TestService6 testService6;

    @GetMapping("/1")
    public String test1(@RequestParam(required = false) String name) {
        return testService1.operation(name);
    }

    @GetMapping("/2")
    public String test2() {
        return testService2.operation();
    }

    @GetMapping("/3")
    public String test3() {
        try {
            return testService3.operation();
        } catch (FallbackException oe) {
            return "FallbackException";
        }
    }

    @GetMapping("/4")
    public String test4() {
        try {
            return testService4.operation();
        } catch (RuntimeException re) {
            return "RuntimeException";
        }
    }

    @GetMapping("/5")
    public String test5(@RequestParam(required = false) String param) {
        return testService5.operation(param);
    }

    @GetMapping("/6")
    public String test6() {
        return testService6.operation();
    }
}

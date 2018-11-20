package br.com.leonardoferreira.poc.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThreadPoolLimit {

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "6000")
    })
    public void slowOperation() {
        log.info("Info=invocando slowOperation");
        sleep();
        log.info("Info=termino slowOperation");
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "6000")
    }, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "15")
    }, threadPoolKey = "big-thread-pool")
    public void slowOperationWithBigThreadPool() {
        log.info("Info=invocando slowOperationWithBigThreadPool");
        sleep();
        log.info("Info=termino slowOperationWithBigThreadPool");
    }

    private void sleep() {
        try {
            Thread.sleep(5_000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

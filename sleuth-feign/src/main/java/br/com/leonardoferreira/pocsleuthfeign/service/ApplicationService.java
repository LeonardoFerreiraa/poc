package br.com.leonardoferreira.pocsleuthfeign.service;

import br.com.leonardoferreira.pocsleuthfeign.client.ApplicationClient;
import brave.Tracing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author lferreira
 * @since 2/5/18 1:43 PM
 */
@Slf4j
@Service
public class ApplicationService {

    @Autowired
    private ApplicationClient applicationClient;

    @Value("${application.allow-propagation}")
    private boolean allowPropagation;

    public String health(Boolean propagate) {
        log.info("M=health, MSG=method init");

        if (Boolean.TRUE.equals(propagate) && allowPropagation) {
            return healthCheck(propagate);
        } else {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    log.error("M=health, e={}", e.getMessage(), e);
                }
                log.info("M=health, iteration={}, tracing={}", i, Tracing.current().tracer().toString());
            }
        }

        return "OK";
    }

    public String healthCheck(Boolean propagate) {
        log.info("M=healthCheck");
        return applicationClient.health(propagate);
    }
}

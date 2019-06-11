package br.com.leonardoferreira.poc.feign.client.config;

import feign.Request;
import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetRetryer extends Retryer.Default {

    @Override
    public void continueOrPropagate(final RetryableException e) {
        log.info("continueOrPropagate");

        if (e.method().equals(Request.HttpMethod.GET)) {
            super.continueOrPropagate(e);
        } else {
            throw e;
        }
    }

    @Override
    public Retryer clone() {
        return new GetRetryer();
    }

}

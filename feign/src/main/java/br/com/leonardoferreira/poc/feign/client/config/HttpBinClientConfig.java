package br.com.leonardoferreira.poc.feign.client.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;

public class HttpBinClientConfig {

    @Bean
    public Retryer retryer() {
        return new GetRetryer();
    }

}

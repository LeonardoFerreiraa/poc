package br.com.leonardoferreira.poc.feign.client.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class ClientConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.insert(0, "http://0.0.0.0:8088");
        };
    }

}

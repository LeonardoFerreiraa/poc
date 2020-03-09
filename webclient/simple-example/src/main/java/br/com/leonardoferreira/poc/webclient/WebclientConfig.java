package br.com.leonardoferreira.poc.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {

    @Bean
    public WebClient httpBinWebClient(final WebClient.Builder builder) {
        return builder
                .baseUrl("https://httpbin.org")
                .build();
    }

    @Bean
    public WebClient localHostClient(final WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8080")
                .build();
    }

}

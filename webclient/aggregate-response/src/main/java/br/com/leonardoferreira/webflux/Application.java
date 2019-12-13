package br.com.leonardoferreira.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebClient httpBinClient() {
        return WebClient.builder()
                .baseUrl("https://httpbin.org")
                .build();
    }

    @Bean
    public WebClient jsonPlaceHolderClient() {
        return WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }

}

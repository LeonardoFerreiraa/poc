package br.com.leonardoferreira.poc.jpasearch;

import com.github.javafaker.Faker;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestConfig {

    @Bean
    public Faker faker() {
        return new Faker();
    }
}

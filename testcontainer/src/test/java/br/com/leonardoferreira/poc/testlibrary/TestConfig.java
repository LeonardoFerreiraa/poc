package br.com.leonardoferreira.poc.testlibrary;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    Faker faker() {
        return Faker.instance();
    }

}

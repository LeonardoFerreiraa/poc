package br.com.leonardoferreira.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

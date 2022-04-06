package br.com.leonardoferreira.poc;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@RestController
class MyController {

    @GetMapping("properties")
    Properties pingPong() {
        final Properties properties = new Properties();
        properties.put("custom.simple", "my-remote-value");
        return properties;
    }

}

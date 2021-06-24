package br.com.leonardoferreira.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class JibApplication {

    public static void main(String[] args) {
        SpringApplication.run(JibApplication.class, args);
    }

}

@RestController
class GreetingController {

    @GetMapping("greetings")
    String greeting() {
        return "hello world";
    }

}

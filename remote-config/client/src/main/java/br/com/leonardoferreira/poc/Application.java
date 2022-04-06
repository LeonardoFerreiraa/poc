package br.com.leonardoferreira.poc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@ConfigurationProperties(prefix = "custom")
record MyProp(String simple) {
}

@RestController
@RequiredArgsConstructor
class MyController {

    private final MyProp myProp;

    @GetMapping("/prop")
    String prop() {
        return myProp.simple();
    }

}

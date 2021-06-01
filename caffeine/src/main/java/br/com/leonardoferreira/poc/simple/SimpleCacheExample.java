package br.com.leonardoferreira.poc.simple;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@EnableCaching
@SpringBootApplication
public class SimpleCacheExample {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SimpleCacheExample.class)
                .properties(
                        Map.of(
                                "spring.cache.cache-names", "greeting, optionalGreeting",
                                "spring.cache.caffeine.spec", "expireAfterAccess=600s"
                        )
                )
                .run(args);
    }

}

@Component
class MyRunner implements ApplicationRunner {

    private final MyService myService;

    MyRunner(final MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        System.out.println(myService.greeting());
        System.out.println(myService.greeting());
        System.out.println(myService.greeting());

        System.out.println(myService.optionalGreeting());
        System.out.println(myService.optionalGreeting());
        System.out.println(myService.optionalGreeting());
    }

}

@Service
class MyService {

    @Cacheable(cacheNames = "greeting")
    public String greeting() {
        System.out.println("greeting");
        return "hello world";
    }

    @Cacheable(cacheNames = "optionalGreeting", unless = "#result == null")
    public Optional<String> optionalGreeting() {
        System.out.println("optionalGreeting");
        return Optional.of("hello world");
    }
}

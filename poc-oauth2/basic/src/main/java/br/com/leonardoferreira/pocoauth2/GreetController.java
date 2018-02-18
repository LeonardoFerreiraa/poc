package br.com.leonardoferreira.pocoauth2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lferreira on 2/17/18
 */
@RestController
public class GreetController {
    @GetMapping("/")
    public Greet index(@RequestParam(required = false, defaultValue = "World") String name) {
        return new Greet(String.format("Hello %s!", name));
    }
}

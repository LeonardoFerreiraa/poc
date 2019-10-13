package br.com.leonardoferreira.metrics;

import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Timed
@RestController
public class SimpleController {

    @GetMapping("/greetings")
    @Timed(value = "greetings.time", longTask = true)
    public String greetings() throws InterruptedException {
        final long millis = (long) (Math.random() * 2000);
        System.out.println(millis);
        Thread.sleep(millis);
        return "hello world";
    }

    @GetMapping("/statuses/{code}")
    public ResponseEntity<String> status(@PathVariable Integer code) {
        final HttpStatus status = HttpStatus.resolve(code);
        return new ResponseEntity<>(status == null ? HttpStatus.BAD_REQUEST : status);
    }

}

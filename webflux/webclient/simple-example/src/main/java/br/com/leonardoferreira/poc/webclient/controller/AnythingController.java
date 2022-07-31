package br.com.leonardoferreira.poc.webclient.controller;

import br.com.leonardoferreira.poc.webclient.domain.ComplexThing;
import br.com.leonardoferreira.poc.webclient.domain.SimpleThing;
import br.com.leonardoferreira.poc.webclient.service.AnythingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

// Disable REST lint :D
@RestController
@RequestMapping("/anything")
public class AnythingController {

    private final AnythingService anythingService;

    public AnythingController(AnythingService anythingService) {
        this.anythingService = anythingService;
    }

    @GetMapping("/simple")
    public Mono<SimpleThing> findSimpleThing() {
        return anythingService.findSimpleThing();
    }

    @GetMapping("/complex")
    public Mono<ComplexThing> findComplexThing() {
        return anythingService.findComplexThing();
    }

    @GetMapping("/not-found")
    public Mono<SimpleThing> notFound() {
        return anythingService.notFound();
    }
}

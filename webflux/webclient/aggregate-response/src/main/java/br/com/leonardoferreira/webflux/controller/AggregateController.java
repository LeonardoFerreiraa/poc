package br.com.leonardoferreira.webflux.controller;

import br.com.leonardoferreira.webflux.domain.AggregatedResponse;
import br.com.leonardoferreira.webflux.service.AggregateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/aggregations")
public class AggregateController {

    private final AggregateService aggregateService;

    public AggregateController(final AggregateService aggregateService) {
        this.aggregateService = aggregateService;
    }

    @GetMapping
    public Mono<AggregatedResponse> aggregations() {
        return aggregateService.call();
    }
}

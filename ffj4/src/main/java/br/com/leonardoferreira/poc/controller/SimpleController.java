package br.com.leonardoferreira.poc.controller;

import br.com.leonardoferreira.poc.domain.SimpleResponse;
import br.com.leonardoferreira.poc.service.FeatureName;
import br.com.leonardoferreira.poc.service.FeatureService;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simples")
public class SimpleController {

    private final FeatureService featureService;

    public SimpleController(final FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping
    public SimpleResponse index() {
        if (featureService.check(FeatureName.UUID_GENERATOR)) {
            return new SimpleResponse(UUID.randomUUID().toString());
        }

        return new SimpleResponse(null);
    }
}

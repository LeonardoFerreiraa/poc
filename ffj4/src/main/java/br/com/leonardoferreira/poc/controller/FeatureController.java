package br.com.leonardoferreira.poc.controller;

import br.com.leonardoferreira.poc.service.FeatureName;
import br.com.leonardoferreira.poc.service.FeatureService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/features/{featureName}")
public class FeatureController {
    private final FeatureService featureService;

    public FeatureController(final FeatureService featureService) {
        this.featureService = featureService;
    }

    @PostMapping("/enable")
    public HttpEntity<?> enable(@PathVariable final FeatureName featureName) {
        featureService.enable(featureName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disable")
    public HttpEntity<?> disable(@PathVariable final FeatureName featureName) {
        featureService.disable(featureName);
        return ResponseEntity.ok().build();
    }
}

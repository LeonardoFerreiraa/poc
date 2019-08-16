package br.com.leonardoferreira.poc.service;

import org.ff4j.FF4j;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {

    private final FF4j ff4j;

    public FeatureService(final FF4j ff4j) {
        this.ff4j = ff4j;
    }

    public boolean check(final FeatureName featureName) {
        return ff4j.check(featureName.name());
    }

    public void enable(final FeatureName featureName) {
        ff4j.enable(featureName.name());
    }

    public void disable(final FeatureName featureName) {
        ff4j.disable(featureName.name());
    }
}

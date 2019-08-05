package br.com.leonardoferreira.poc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PrintPropertyRunner implements CommandLineRunner {

    private final DefaultPropertyMapping defaultPropertyMapping;

    public PrintPropertyRunner(final DefaultPropertyMapping defaultPropertyMapping) {
        this.defaultPropertyMapping = defaultPropertyMapping;
    }

    @Override
    public void run(final String... args) throws Exception {
        System.out.println(defaultPropertyMapping.getMergedProperties());
    }
}

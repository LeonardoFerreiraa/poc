package br.com.leonardoferreira.poc.feign.runner;

import br.com.leonardoferreira.poc.feign.client.HttpBinClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class HttpBinRunner implements CommandLineRunner {

    private final HttpBinClient httpBinClient;

    public HttpBinRunner(HttpBinClient httpBinClient) {
        this.httpBinClient = httpBinClient;
    }

    @Override
    public void run(String... args) throws Exception {
        httpBinClient.retry();
    }
}

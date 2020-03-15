package br.com.leonardoferreira.poc.feign.controller;

import br.com.leonardoferreira.poc.feign.client.HttpBinClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/http-bin")
public class HttpBinController {

    private final HttpBinClient httpBinClient;

    public HttpBinController(HttpBinClient httpBinClient) {
        this.httpBinClient = httpBinClient;
    }

    @GetMapping
    public String retry() {
        return httpBinClient.retry();
    }

}

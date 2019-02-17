package br.com.leonardoferreira.poc.feign.client;

import br.com.leonardoferreira.poc.feign.client.config.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "secondHttpbinClient",
        url = "https://httpbin.org/",
        configuration = ClientConfiguration.class)
public interface SecondHttpBinClient {

    @GetMapping("/anything")
    String anything();

}

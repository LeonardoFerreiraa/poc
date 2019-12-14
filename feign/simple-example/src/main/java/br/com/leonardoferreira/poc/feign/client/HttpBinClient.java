package br.com.leonardoferreira.poc.feign.client;

import br.com.leonardoferreira.poc.feign.client.config.HttpBinClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "httpBinClient",
        url = "${httpbin.url}",
        configuration = {
                HttpBinClientConfig.class
        }
)
public interface HttpBinClient {

    @GetMapping("/status/500")
    String retry();

}

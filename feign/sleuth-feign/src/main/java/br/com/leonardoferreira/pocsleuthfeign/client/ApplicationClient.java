package br.com.leonardoferreira.pocsleuthfeign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lferreira
 * @since 2/5/18 1:41 PM
 */
@FeignClient(name = "application-client", url = "${application.url}")
public interface ApplicationClient {

    @GetMapping("/myep")
    String health(@RequestParam(required = false, defaultValue = "false") Boolean propagate);

}

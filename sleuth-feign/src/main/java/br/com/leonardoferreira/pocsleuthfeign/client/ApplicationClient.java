package br.com.leonardoferreira.pocsleuthfeign.client;

import com.netflix.ribbon.proxy.annotation.Http;
import feign.Headers;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

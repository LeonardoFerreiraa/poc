package br.com.leonardoferreira.poc.httpclient.client;

import br.com.leonardoferreira.poc.httpclient.annotation.HttpClient;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.HttpBinRequest;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.HttpBinResponse;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.IpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

@HttpClient(name = "httpBinClient", url = "https://httpbin.org/")
public interface HttpBinClient {

    @RequestMapping(method = RequestMethod.GET, value = "/ip")
    Mono<IpResponse> retrieveIP();

    @RequestMapping(method = RequestMethod.POST, value = "/anything")
    Mono<String> anything(@RequestBody HttpBinRequest httpBinRequest,
                                   @RequestHeader("Content-Type") String contentType);

}

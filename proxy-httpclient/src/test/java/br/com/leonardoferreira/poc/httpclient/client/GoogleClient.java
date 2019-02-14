package br.com.leonardoferreira.poc.httpclient.client;

import br.com.leonardoferreira.poc.httpclient.annotation.Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

@Client(url = "https://www.google.com")
public interface GoogleClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    Mono<String> homePage();

}

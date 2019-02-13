package br.com.leonardoferreira.poc.httpclient.client;

import br.com.leonardoferreira.poc.httpclient.Method;
import br.com.leonardoferreira.poc.httpclient.annotation.Body;
import br.com.leonardoferreira.poc.httpclient.annotation.Client;
import br.com.leonardoferreira.poc.httpclient.annotation.HttpRequest;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.HttpBinRequest;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.HttpBinResponse;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.IpResponse;

@Client(url = "https://httpbin.org/")
public interface HttpBinClient {

    @HttpRequest(method = Method.GET, url = "/ip")
    IpResponse retrieveIP();

    @HttpRequest(method = Method.POST, url = "/anything")
    HttpBinResponse anything(@Body HttpBinRequest httpBinRequest);

}

package br.com.leonardoferreira.poc.httpclient.client;

import br.com.leonardoferreira.poc.httpclient.Method;
import br.com.leonardoferreira.poc.httpclient.annotation.Client;
import br.com.leonardoferreira.poc.httpclient.annotation.HttpRequest;

@Client(url = "https://google.com")
public interface GoogleClient {

    @HttpRequest(method = Method.GET, url = "/")
    String homePage();

}

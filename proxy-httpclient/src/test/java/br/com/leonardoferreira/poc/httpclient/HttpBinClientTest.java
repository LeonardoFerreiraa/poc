package br.com.leonardoferreira.poc.httpclient;

import br.com.leonardoferreira.poc.httpclient.client.HttpBinClient;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.HttpBinRequest;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.HttpBinResponse;
import br.com.leonardoferreira.poc.httpclient.domain.httpbin.IpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpBinClientTest {

    private HttpBinClient httpBinClient;

    @BeforeEach
    void setUp() {
        httpBinClient = ClientBuilder
                .of(HttpBinClient.class)
                .build();
    }

    @Test
    void printsIp() {
        IpResponse ipResponse = httpBinClient.retrieveIP();
        System.out.println(ipResponse);
    }

    @Test
    void postRequest() {
        HttpBinRequest request = new HttpBinRequest("test-name");

        HttpBinResponse response = httpBinClient.anything(request);

        System.out.println(response);
    }
}

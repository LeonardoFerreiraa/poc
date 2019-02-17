package br.com.leonardoferreira.poc.feign.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpBinClientTest {

    @Autowired
    private HttpBinClient httpBinClient;

    @Autowired
    private SecondHttpBinClient secondHttpBinClient;

    @Test
    void configuration() {
        System.out.println(httpBinClient.anything());
        System.out.println(secondHttpBinClient.anything());
    }
}

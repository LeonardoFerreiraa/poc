package br.com.leonardoferreira.poc.httpclient;

import br.com.leonardoferreira.poc.httpclient.client.GoogleClient;
import org.junit.jupiter.api.Test;

public class GoogleClientTest {

    @Test
    void printsHomePage() {
        GoogleClient googleClient = ClientBuilder
                .of(GoogleClient.class)
                .build();

        System.out.println(googleClient.homePage());
    }

}

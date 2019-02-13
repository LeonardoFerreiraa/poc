package br.com.leonardoferreira.poc.httpclient.domain.httpbin;

public class HttpBinRequest {

    private String name;

    public HttpBinRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

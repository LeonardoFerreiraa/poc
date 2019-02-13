package br.com.leonardoferreira.poc.httpclient.domain.httpbin;

public class IpResponse {

    private String origin;

    @Override
    public String toString() {
        return "IpResponse{" +
                "origin='" + origin + '\'' +
                '}';
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}

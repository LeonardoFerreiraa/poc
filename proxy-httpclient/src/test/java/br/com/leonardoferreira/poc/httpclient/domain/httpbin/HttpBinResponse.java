package br.com.leonardoferreira.poc.httpclient.domain.httpbin;

import java.util.Map;

public class HttpBinResponse {

    private String method;

    private String origin;

    private String url;

    private Map<String, Object> json;

    @Override
    public String toString() {
        return "HttpBinResponse{" +
                "method='" + method + '\'' +
                ", origin='" + origin + '\'' +
                ", url='" + url + '\'' +
                ", json=" + json +
                '}';
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getJson() {
        return json;
    }

    public void setJson(Map<String, Object> json) {
        this.json = json;
    }
}

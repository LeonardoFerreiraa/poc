package br.com.leonardoferreira.poc.httpclient.domain.httpbin;

import java.util.Map;
import lombok.Data;

@Data
public class HttpBinResponse {

    private String method;

    private String origin;

    private String url;

    private Map<String, Object> json;

}

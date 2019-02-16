package br.com.leonardoferreira.poc.httpclient.request;

import br.com.leonardoferreira.poc.httpclient.domain.RequestMethod;
import java.util.Map;

public interface RequestAttributes {

    RequestMethod getMethod();

    String getUri();

    Object getBody();

    Map<String, String> getHeaders();

    Map<String, Object> getAttributes();

}

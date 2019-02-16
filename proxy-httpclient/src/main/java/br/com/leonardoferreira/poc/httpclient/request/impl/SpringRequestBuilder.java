package br.com.leonardoferreira.poc.httpclient.request.impl;

import br.com.leonardoferreira.poc.httpclient.request.RequestAttributes;
import br.com.leonardoferreira.poc.httpclient.request.RequestBuilder;
import java.lang.reflect.Method;
import org.springframework.web.bind.annotation.RequestMapping;

public class SpringRequestBuilder implements RequestBuilder {

    @Override
    public RequestAttributes build(Method method, Object[] args) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            return null;
        }

        return new SpringRequestAttributes(requestMapping, method, args);
    }

}

package br.com.leonardoferreira.poc.httpclient.request;

import java.lang.reflect.Method;

public interface RequestBuilder {

    RequestAttributes build(Method method, Object[] args);

}

package br.com.leonardoferreira.poc.httpclient.annotation;

import br.com.leonardoferreira.poc.httpclient.Method;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HttpRequest {

    Method method();

    String url();

}

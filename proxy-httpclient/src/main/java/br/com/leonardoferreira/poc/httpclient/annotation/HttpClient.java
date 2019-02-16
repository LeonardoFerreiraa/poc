package br.com.leonardoferreira.poc.httpclient.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HttpClient {

    String url();

}

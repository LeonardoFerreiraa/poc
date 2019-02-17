package br.com.leonardoferreira.poc.httpclient.annotation;

import br.com.leonardoferreira.poc.httpclient.configuration.HttpClientConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HttpClientConfiguration.class)
public @interface EnableHttpClient {

}

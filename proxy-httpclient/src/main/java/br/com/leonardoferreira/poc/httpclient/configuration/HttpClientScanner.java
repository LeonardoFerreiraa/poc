package br.com.leonardoferreira.poc.httpclient.configuration;

import br.com.leonardoferreira.poc.httpclient.annotation.HttpClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class HttpClientScanner extends ClassPathScanningCandidateComponentProvider {

    public HttpClientScanner(Environment environment, ResourceLoader resourceLoader) {
        super(false, environment);
        super.setResourceLoader(resourceLoader);
        super.addIncludeFilter(new AnnotationTypeFilter(HttpClient.class));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isIndependent()
                && !beanDefinition.getMetadata().isAnnotation();
    }
}
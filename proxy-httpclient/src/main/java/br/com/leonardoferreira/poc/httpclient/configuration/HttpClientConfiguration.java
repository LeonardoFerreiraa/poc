package br.com.leonardoferreira.poc.httpclient.configuration;

import br.com.leonardoferreira.poc.httpclient.annotation.HttpClient;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

@Slf4j
public class HttpClientConfiguration implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        HttpClientScanner scanner = new HttpClientScanner(environment, resourceLoader);

        String basePackage = ClassUtils.getPackageName(metadata.getClassName());
        for (BeanDefinition candidateComponent : scanner.findCandidateComponents(basePackage)) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = annotatedBeanDefinition.getMetadata();
                Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(HttpClient.class.getCanonicalName());

                BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(HttpClientFactory.class);
                definition.addPropertyValue("type", annotationMetadata.getClassName());

                registry.registerBeanDefinition(extractName(attributes), definition.getBeanDefinition());
            }
        }
    }

    private String extractName(Map<String, Object> attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException();
        }

        return (String) attributes.get("name");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}

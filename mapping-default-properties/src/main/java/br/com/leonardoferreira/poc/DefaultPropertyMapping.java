package br.com.leonardoferreira.poc;

import java.util.Collection;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@ConfigurationProperties("my-properties")
public class DefaultPropertyMapping implements ApplicationContextAware {

    private Map<String, CustomProperty> properties;

    private ApplicationContext applicationContext;

    public Collection<CustomProperty> getMergedProperties() {
        return properties.values();
    }

    @Override
    public String toString() {
        return "DefaultPropertyMapping{" +
                "properties=" + properties +
                '}';
    }

    public Map<String, CustomProperty> getProperties() {
        return properties;
    }

    public void setProperties(final Map<String, CustomProperty> properties) {
        this.properties = properties;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private CustomProperty bindToDefault() {
        CustomProperty extendedBindingPropertiesTarget = BeanUtils.instantiateClass(CustomProperty.class);
        Binder binder = new Binder(
                ConfigurationPropertySources.get(this.applicationContext.getEnvironment()),
                new PropertySourcesPlaceholdersResolver(this.applicationContext.getEnvironment()),
                null,
                null
        );
        binder.bind("my-properties.default", Bindable.ofInstance(extendedBindingPropertiesTarget));
        return extendedBindingPropertiesTarget;
    }
}

package br.com.leonardoferreira.poc;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("my-properties")
public class DefaultPropertyMapping {

    private Map<String, CustomProperty> properties;


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

}

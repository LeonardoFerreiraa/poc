package br.com.leonardoferreira.poc;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MyConfigDataLoader implements ConfigDataLoader<MyConfigDataResource> {

    @Override
    public ConfigData load(final ConfigDataLoaderContext context, final MyConfigDataResource resource) throws IOException, ConfigDataResourceNotFoundException {
        final Properties properties = doLoad(context);
        List<PropertySource<?>> sources = List.of(new OriginTrackedMapPropertySource("myrc:", properties));
        return new ConfigData(sources, ConfigData.PropertySourceOptions.always(ConfigData.Option.IGNORE_PROFILES));
    }

    public Properties doLoad(final ConfigDataLoaderContext context) {
        final RestTemplate restTemplate = context.getBootstrapContext()
                .getOrElseSupply(RestTemplate.class, RestTemplate::new);

        final ResponseEntity<Properties> forEntity = restTemplate.getForEntity("http://localhost:8080/properties", Properties.class);
        return forEntity.getBody();
    }

}

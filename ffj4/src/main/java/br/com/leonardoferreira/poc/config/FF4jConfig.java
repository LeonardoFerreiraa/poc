package br.com.leonardoferreira.poc.config;

import javax.sql.DataSource;
import org.ff4j.FF4j;
import org.ff4j.cache.InMemoryCacheManager;
import org.ff4j.springjdbc.store.EventRepositorySpringJdbc;
import org.ff4j.springjdbc.store.FeatureStoreSpringJdbc;
import org.ff4j.springjdbc.store.PropertyStoreSpringJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4jConfig {

    @Bean
    public FF4j ff4j(final DataSource dataSource) {
        final FF4j ff4j = new FF4j();

        ff4j.setFeatureStore(new FeatureStoreSpringJdbc(dataSource));
        ff4j.setPropertiesStore(new PropertyStoreSpringJdbc(dataSource));
        ff4j.setEventRepository(new EventRepositorySpringJdbc(dataSource));
        ff4j.cache(new InMemoryCacheManager());
        ff4j.setAutocreate(true);

        return ff4j;
    }

}

package br.com.leonardoferreira.poc.config.database;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class DatabaseContainerContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        final PostgresContainer postgresContainer = PostgresContainer.INSTANCE;

        final TestPropertyValues properties = TestPropertyValues.of(
                "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgresContainer.getUsername(),
                "spring.datasource.password=" + postgresContainer.getPassword()
        );

        properties.applyTo(applicationContext.getEnvironment());
    }

}

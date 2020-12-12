package br.com.leonardoferreira.poc.testlibrary.applicationcontextinitializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class DatabaseContainerContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + SingletonPostgresContainer.getInstance().getJdbcUrl(),
                "spring.datasource.username=" + SingletonPostgresContainer.getInstance().getUsername(),
                "spring.datasource.password=" + SingletonPostgresContainer.getInstance().getPassword()
        ).applyTo(applicationContext.getEnvironment());
    }

}


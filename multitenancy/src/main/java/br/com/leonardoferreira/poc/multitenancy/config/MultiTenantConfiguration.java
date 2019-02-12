package br.com.leonardoferreira.poc.multitenancy.config;

import javax.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MultiTenantConfiguration {

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        MultiTenantJpaTransactionManager multitenantJpaTransactionManager = new MultiTenantJpaTransactionManager();
        multitenantJpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return multitenantJpaTransactionManager;
    }

}

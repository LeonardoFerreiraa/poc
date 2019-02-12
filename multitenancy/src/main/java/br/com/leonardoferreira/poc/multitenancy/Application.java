package br.com.leonardoferreira.poc.multitenancy;

import br.com.leonardoferreira.poc.multitenancy.config.MultitenantJpaTransactionManager;
import br.com.leonardoferreira.poc.multitenancy.domain.Account;
import java.util.Optional;
import javax.persistence.EntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaAuditing
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return Optional.empty();
            }
            Account principal = (Account) authentication.getPrincipal();
            return Optional.ofNullable(principal.getUsername());
        };
    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        MultitenantJpaTransactionManager multitenantJpaTransactionManager = new MultitenantJpaTransactionManager();
        multitenantJpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return multitenantJpaTransactionManager;
    }

}


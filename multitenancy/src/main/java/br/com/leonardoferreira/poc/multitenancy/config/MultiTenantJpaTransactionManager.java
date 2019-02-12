package br.com.leonardoferreira.poc.multitenancy.config;

import br.com.leonardoferreira.poc.multitenancy.domain.Account;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class MultiTenantJpaTransactionManager extends JpaTransactionManager {

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        super.doBegin(transaction, definition);
        EntityManagerFactory entityManagerFactory = getEntityManagerFactory();

        if (entityManagerFactory != null) {
            EntityManagerHolder entityManagerHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(entityManagerFactory);

            if (entityManagerHolder != null) {
                EntityManager entityManager = entityManagerHolder.getEntityManager();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication != null) {
                    Account principal = (Account) authentication.getPrincipal();
                    entityManager.unwrap(Session.class)
                            .enableFilter("tenantFilter")
                            .setParameter("currentUser", principal.getUsername());
                }
            }
        }
    }

}

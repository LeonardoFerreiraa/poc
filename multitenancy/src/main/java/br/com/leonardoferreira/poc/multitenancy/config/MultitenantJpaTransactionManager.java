package br.com.leonardoferreira.poc.multitenancy.config;

import br.com.leonardoferreira.poc.multitenancy.domain.Account;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class MultitenantJpaTransactionManager extends JpaTransactionManager {

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        super.doBegin(transaction, definition);
        EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager
                .getResource(getEntityManagerFactory())).getEntityManager();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Account principal = (Account) authentication.getPrincipal();
            Session session = em.unwrap(Session.class);
            session.enableFilter("tenantFilter")
                    .setParameter("currentUser", principal.getUsername());
        }
    }

}

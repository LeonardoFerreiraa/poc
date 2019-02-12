package br.com.leonardoferreira.poc.multitenancy.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantHolder implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return null;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}

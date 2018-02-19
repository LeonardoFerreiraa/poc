package br.com.leonardoferreira.test.service;

import br.com.leonardoferreira.test.domain.Account;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by lferreira on 2/18/18
 */
public class ApplicationService {
    protected Account currentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return null;
        }

        return (Account) principal;
    }

}

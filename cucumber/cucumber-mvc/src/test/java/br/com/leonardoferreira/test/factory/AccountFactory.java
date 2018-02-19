package br.com.leonardoferreira.test.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import br.com.leonardoferreira.test.domain.Account;
import br.com.leonardoferreira.test.repository.AccountRepository;
import com.github.javafaker.Faker;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Locale;

/**
 * Created by lferreira on 2/18/18
 */
@Component
@Scope("prototype")
public class AccountFactory extends JBacon<Account> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Getter
    private Account currentUser;

    @Override
    protected Account getDefault() {
        Faker faker = new Faker(new Locale("pt", "BR"));
        Account account = new Account();
        account.setUsername(faker.internet().emailAddress());
        account.setName(faker.name().fullName());
        account.setPassword(passwordEncoder.encode("123123"));
        return account;
    }

    @Override
    protected Account getEmpty() {
        return new Account();
    }

    @Override
    protected void persist(Account account) {
        accountRepository.save(account);
    }

    public RequestPostProcessor authenticatedUser() {
        currentUser = this.create();
        return SecurityMockMvcRequestPostProcessors.user(currentUser);
    }
}

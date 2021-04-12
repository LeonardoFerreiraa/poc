package br.com.leonardoferreira.poc.restspringsecurity;

import br.com.leonardoferreira.poc.restspringsecurity.domain.Account;
import br.com.leonardoferreira.poc.restspringsecurity.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner dataInitializer(final PasswordEncoder passwordEncoder,
                                             final AccountRepository accountRepository) {
        return args -> {
            Account normalUser = new Account();
            normalUser.setUsername("normalUser");
            normalUser.setUsername(passwordEncoder.encode("123123"));
            accountRepository.save(normalUser);

            Account admin = new Account();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("321321"));
            accountRepository.save(admin);
        };
    }

}


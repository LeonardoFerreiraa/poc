package br.com.leonardoferreira.test;

import br.com.leonardoferreira.test.domain.Account;
import br.com.leonardoferreira.test.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    @Profile("!test")
	public CommandLineRunner createUser(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String username = "teste@email.com";
            Account account = accountRepository.findByUsername(username);
            if (account == null) {
                account = new Account();
                account.setUsername(username);
                account.setPassword(passwordEncoder.encode("123123"));
                account.setName("Account Name");
                accountRepository.save(account);
            }
        };
    }
}

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
	public CommandLineRunner dataInitializer(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
		return args -> {
			Account normaluser = new Account();
			normaluser.setUsername("normaluser");
			normaluser.setUsername(passwordEncoder.encode("123123"));
			accountRepository.save(normaluser);

			Account admin = new Account();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("321321"));
			accountRepository.save(admin);
		};
	}

}


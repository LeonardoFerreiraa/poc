package br.com.leonardoferreira.report;

import br.com.leonardoferreira.report.domain.Project;
import br.com.leonardoferreira.report.repository.ProjectRepository;
import br.com.leonardoferreira.report.security.domain.Account;
import br.com.leonardoferreira.report.security.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Seed implements CommandLineRunner {

    @Autowired
    private AuthRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void run(String... args) throws Exception {
        Account account = new Account();
        account.setUsername("username");
        account.setPassword(passwordEncoder.encode("password"));

        for (int i = 0; i < 50; i++) {
            Project project = new Project();
            project.setName("project_" + i);
            projectRepository.save(project);
        }

        accountRepository.save(account);
    }
}

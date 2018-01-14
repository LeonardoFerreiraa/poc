package br.com.leonardoferreira.poc.mapstruct;

import br.com.leonardoferreira.poc.mapstruct.domain.entity.TitleEnum;
import br.com.leonardoferreira.poc.mapstruct.domain.entity.User;
import br.com.leonardoferreira.poc.mapstruct.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CmdRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFirstname(faker.name().firstName());
            user.setLastname(faker.name().lastName());
            TitleEnum[] values = TitleEnum.values();
            user.setTitle(values[i % 2]);
            if (i % 2 == 0) {
                user.setAddress(faker.address().fullAddress());
            }
            userRepository.save(user);
        }

    }
}

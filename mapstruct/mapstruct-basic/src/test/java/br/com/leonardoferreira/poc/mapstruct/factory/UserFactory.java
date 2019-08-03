package br.com.leonardoferreira.poc.mapstruct.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import br.com.leonardoferreira.poc.mapstruct.domain.entity.TitleEnum;
import br.com.leonardoferreira.poc.mapstruct.domain.entity.User;
import br.com.leonardoferreira.poc.mapstruct.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class UserFactory extends JBacon<User> {

    private final Faker faker;

    private final UserRepository userRepository;

    public UserFactory(final Faker faker, final UserRepository userRepository) {
        this.faker = faker;
        this.userRepository = userRepository;
    }

    @Override
    protected User getDefault() {
        final User user = new User();

        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setAddress(faker.address().fullAddress());
        user.setTitle(faker.options().option(TitleEnum.class));

        return user;
    }

    @Override
    protected User getEmpty() {
        return new User();
    }

    @Override
    protected void persist(final User user) {
        userRepository.save(user);
    }

}

package br.com.leonardoferreira.test;

import br.com.leonardoferreira.test.domain.Contact;
import br.com.leonardoferreira.test.domain.Phone;
import br.com.leonardoferreira.test.repository.ContactRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CmdRunner implements CommandLineRunner {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void run(String... args) throws Exception {
        if (contactRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.setName(faker.name().fullName());
            contact.setPhone(new Phone(faker.phoneNumber().phoneNumber()));
            contact.setEmail(faker.internet().emailAddress());
            contactRepository.save(contact);
        }
    }
}

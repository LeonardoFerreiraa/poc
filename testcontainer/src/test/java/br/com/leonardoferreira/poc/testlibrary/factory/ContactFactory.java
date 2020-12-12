package br.com.leonardoferreira.poc.testlibrary.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import br.com.leonardoferreira.poc.domain.entity.Contact;
import br.com.leonardoferreira.poc.repository.ContactRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactFactory extends JBacon<Contact> {

    private final ContactRepository contactRepository;

    private final Faker faker;

    @Override
    protected Contact getDefault() {
        final Contact contact = new Contact();
        contact.setName(faker.gameOfThrones().character());
        contact.setEmail(faker.internet().emailAddress());
        return contact;
    }

    @Override
    protected Contact getEmpty() {
        return new Contact();
    }

    @Override
    protected void persist(final Contact contact) {
        contactRepository.save(contact);
    }
}

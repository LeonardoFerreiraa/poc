package br.com.leonardoferreira.poc.restdocs.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import br.com.leonardoferreira.poc.restdocs.domain.Contact;
import br.com.leonardoferreira.poc.restdocs.repository.ContactRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class ContactFactory extends JBacon<Contact> {

    private final Faker faker;

    private final ContactRepository contactRepository;

    public ContactFactory(final Faker faker,
                          final ContactRepository contactRepository) {
        this.faker = faker;
        this.contactRepository = contactRepository;
    }

    @Override
    protected Contact getDefault() {
        Contact contact = new Contact();

        contact.setName(faker.gameOfThrones().character());
        contact.setEmail(faker.internet().emailAddress());

        return contact;
    }

    @Override
    protected Contact getEmpty() {
        return new Contact();
    }

    @Override
    protected void persist(Contact contact) {
        contactRepository.save(contact);
    }

}

package br.com.leonardoferreira.poc.jpasearch.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import br.com.leonardoferreira.poc.jpasearch.domain.Contact;
import br.com.leonardoferreira.poc.jpasearch.repository.ContactRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class ContactFactory extends JBacon<Contact> {

    private final ContactRepository contactRepository;

    private final Faker faker;

    public ContactFactory(ContactRepository contactRepository, Faker faker) {
        this.contactRepository = contactRepository;
        this.faker = faker;
    }

    @Override
    protected Contact getDefault() {
        Contact contact = new Contact();

        contact.setName(faker.name().fullName());
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

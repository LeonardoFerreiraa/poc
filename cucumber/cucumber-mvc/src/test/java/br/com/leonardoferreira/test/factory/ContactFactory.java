package br.com.leonardoferreira.test.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import br.com.leonardoferreira.test.domain.Contact;
import br.com.leonardoferreira.test.domain.Phone;
import br.com.leonardoferreira.test.repository.ContactRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ContactFactory extends JBacon<Contact> {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact getDefault() {
        Faker faker = new Faker(new Locale("pt", "BR"));
        Contact contact = new Contact();
        contact.setName(faker.name().fullName());
        contact.setPhone(new Phone(faker.phoneNumber().phoneNumber()));
        contact.setEmail(faker.internet().emailAddress());
        return contact;
    }

    @Override
    public Contact getEmpty() {
        return new Contact();
    }

    @Override
    public void persist(final Contact contact) {
        contactRepository.save(contact);
    }
}

package br.com.leonardoferreira.test.factory;

import br.com.leonardoferreira.test.domain.Contact;
import br.com.leonardoferreira.test.domain.Phone;
import br.com.leonardoferreira.test.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class ContactFactory extends AbstractFactory<Contact, Long> {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    protected Contact getDefault() {
        Contact contact = new Contact();
        contact.setName(faker.name().fullName());
        contact.setPhone(new Phone(faker.phoneNumber().phoneNumber()));
        contact.setEmail(faker.internet().emailAddress());
        return contact;
    }

    @Override
    protected Contact getEmpty() {
        return new Contact();
    }

    @Override
    protected CrudRepository<Contact, Long> getRepository() {
        return contactRepository;
    }
}

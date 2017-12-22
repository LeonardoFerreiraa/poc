package br.com.leonardoferreira.hello.factory;

import br.com.leonardoferreira.hello.domain.Contact;
import br.com.leonardoferreira.hello.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by lferreira on 7/1/17.
 */
@Component
public class ContactFactory extends AbstractFactory<Contact, Long> {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    protected Contact getDefault() {
        final Contact contact = new Contact();

        contact.setName(faker.name().fullName());
        contact.setEmail(faker.internet().emailAddress());
        contact.setPhone(faker.phoneNumber().cellPhone());

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

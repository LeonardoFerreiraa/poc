package br.com.leonardoferreira.hello.factory;

import br.com.leonardoferreira.hello.domain.Contact;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by lferreira on 7/1/17.
 */
@Component
public class ContactFactory extends SpringDataFactory<Contact> {

    @Override
    public Contact getDefault() {
        Faker faker = new Faker(new Locale("pt", "BR"));
        Contact contact = new Contact();
        contact.setName(faker.name().fullName());
        contact.setEmail(faker.internet().emailAddress());
        contact.setPhone(faker.phoneNumber().phoneNumber());
        return contact;
    }

    @Override
    public Contact getEmpty() {
        return new Contact();
    }

}

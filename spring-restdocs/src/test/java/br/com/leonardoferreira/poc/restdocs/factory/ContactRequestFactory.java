package br.com.leonardoferreira.poc.restdocs.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import br.com.leonardoferreira.poc.restdocs.domain.request.ContactRequest;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class ContactRequestFactory extends JBacon<ContactRequest> {

    private final Faker faker;

    public ContactRequestFactory(final Faker faker) {
        this.faker = faker;
    }

    @Override
    protected ContactRequest getDefault() {
        ContactRequest contactRequest = new ContactRequest();

        contactRequest.setName(faker.gameOfThrones().character());
        contactRequest.setEmail(faker.internet().emailAddress());

        return contactRequest;
    }

    @Override
    protected ContactRequest getEmpty() {
        return new ContactRequest();
    }

    @Override
    protected void persist(ContactRequest contactRequest) {
        throw new UnsupportedOperationException();
    }
}

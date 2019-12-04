package br.com.leonardoferreira.poc;

import br.com.leonardoferreira.poc.domain.Contact;
import br.com.leonardoferreira.poc.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateContactTest {

    private final ContactRepository contactRepository;

    @Autowired
    CreateContactTest(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Test
    void createWithSuccess() {
        final Contact contact = new Contact();

        contact.setName("Leonardo");
        contact.setEmail("mail@leonardoferreira.com.br");

        contactRepository.save(contact);
    }

}

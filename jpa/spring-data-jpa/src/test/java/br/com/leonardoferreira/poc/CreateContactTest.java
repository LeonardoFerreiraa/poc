package br.com.leonardoferreira.poc;

import static junit.framework.TestCase.assertEquals;

import br.com.leonardoferreira.poc.domain.Contact;
import br.com.leonardoferreira.poc.domain.Phone;
import br.com.leonardoferreira.poc.repository.ContactRepository;
import br.com.leonardoferreira.poc.repository.PhoneRepository;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
class CreateContactTest {

    private final ContactRepository contactRepository;

    private final PhoneRepository phoneRepository;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    CreateContactTest(final ContactRepository contactRepository,
                      final PhoneRepository phoneRepository,
                      final TransactionTemplate transactionTemplate) {
        this.contactRepository = contactRepository;
        this.phoneRepository = phoneRepository;
        this.transactionTemplate = transactionTemplate;
    }

    @Test
    void createWithSuccess() {
        final Contact contact = new Contact();

        contact.setName("Leonardo");
        contact.setEmail("mail@leonardoferreira.com.br");
        contact.setPhones(
                Set.of(
                        new Phone("(11) 11111-1111"),
                        new Phone("(22) 22222-2222"),
                        new Phone("(33) 33333-3333")
                )
        );

        transactionTemplate.execute(transactionStatus ->
            contactRepository.save(contact)
        );


        assertEquals(1, contactRepository.count());
        assertEquals(3, phoneRepository.count());
    }

}

package br.com.leonardoferreira.poc.jpasearch.test;

import br.com.leonardoferreira.poc.jpasearch.Application;
import br.com.leonardoferreira.poc.jpasearch.CleanDatabase;
import br.com.leonardoferreira.poc.jpasearch.TestConfig;
import br.com.leonardoferreira.poc.jpasearch.domain.Contact;
import br.com.leonardoferreira.poc.jpasearch.domain.Phone;
import br.com.leonardoferreira.poc.jpasearch.factory.ContactFactory;
import br.com.leonardoferreira.poc.jpasearch.factory.PhoneFactory;
import br.com.leonardoferreira.poc.jpasearch.repository.ContactRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpecificationTest {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private CleanDatabase cleanDatabase;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PhoneFactory phoneFactory;

    @Test
    public void findAllWithSpecificationOfNameAndEmail() {
        List<Contact> contacts = contactFactory.create(5);
        Contact example = contacts.get(0);

        List<Contact> all = contactRepository.findAll((from, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            from.fetch("phones", JoinType.LEFT);

            if (example.getName() != null) {
                predicates.add(builder.equal(from.get("name"), example.getName()));
            }

            if (example.getEmail() != null) {
                predicates.add(builder.equal(from.get("email"), example.getEmail()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        Assertions.assertThat(all)
                .hasSize(1);
        Contact contact = all.get(0);
        Assertions.assertThat(contact.getName())
                .isEqualTo(example.getName());
        Assertions.assertThat(contact.getEmail())
                .isEqualTo(example.getEmail());
        Assertions.assertThat(contact.getPhones())
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void findBySpecificationDate() {
        List<Contact> contacts = contactFactory.create(5);

        List<Contact> all = contactRepository.findAll((from, query, builder) -> {
            from.fetch("phones", JoinType.LEFT);
            return builder.between(from.get("createdAt"), LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        });

        Assertions.assertThat(all)
                .usingElementComparator(Comparator.comparing(Contact::getEmail))
                .hasSameElementsAs(contacts);
    }

    @Test
    public void findBySpecificationPhones() {
        List<Contact> contacts = contactFactory.create(5);
        Contact contact = contacts.get(0);
        List<Phone> phones = phoneFactory.create(2, Phone.builder().contact(contact).build());

        List<Contact> all = contactRepository.findAll((from, query, builder) -> {
            from.fetch("phones");
            query.distinct(true);

            return from.join("phones").in(phones.stream()
                    .map(Phone::getId)
                    .collect(Collectors.toList()));
        });

        Assertions.assertThat(all)
                .hasSize(1);
        Assertions.assertThat(all.get(0).getEmail())
                .isEqualTo(contact.getEmail());
    }
}

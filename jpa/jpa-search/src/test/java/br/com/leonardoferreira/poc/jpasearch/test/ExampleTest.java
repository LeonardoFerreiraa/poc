package br.com.leonardoferreira.poc.jpasearch.test;

import br.com.leonardoferreira.poc.jpasearch.Application;
import br.com.leonardoferreira.poc.jpasearch.CleanDatabase;
import br.com.leonardoferreira.poc.jpasearch.TestConfig;
import br.com.leonardoferreira.poc.jpasearch.domain.Contact;
import br.com.leonardoferreira.poc.jpasearch.factory.ContactFactory;
import br.com.leonardoferreira.poc.jpasearch.repository.ContactRepository;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ExampleTest {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CleanDatabase cleanDatabase;

    @Autowired
    private Faker faker;

    @Before
    public void setup() {
        cleanDatabase.clean();
    }

    @Test
    @Transactional
    public void findAll() {
        List<Contact> contacts = contactFactory.create(5);

        List<Contact> all = (List<Contact>) contactRepository.findAll();
        Assertions.assertThat(all)
                .containsExactlyInAnyOrderElementsOf(contacts);
    }

    @Test
    @Transactional
    public void findAllWithExampleOfNameAndEmail() {
        List<Contact> contacts = contactFactory.create(5);
        Contact contact = contacts.get(faker.random().nextInt(contacts.size()));

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Contact> example = Example.of(contact, matcher);

        List<Contact> all = contactRepository.findAll(example);
        Assertions.assertThat(all)
                .hasSize(1);
        Assertions.assertThat(all.get(0))
                .isEqualTo(contact);
    }

}

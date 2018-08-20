package br.com.leonardoferreira.poc.jpasearch.test;

import br.com.leonardoferreira.poc.jpasearch.Application;
import br.com.leonardoferreira.poc.jpasearch.CleanDatabase;
import br.com.leonardoferreira.poc.jpasearch.TestConfig;
import br.com.leonardoferreira.poc.jpasearch.domain.Contact;
import br.com.leonardoferreira.poc.jpasearch.domain.ContactWrapper;
import br.com.leonardoferreira.poc.jpasearch.factory.ContactFactory;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PredicateTest {

    @Autowired
    private ContactFactory contactFactory;

    @Autowired
    private CleanDatabase cleanDatabase;

    @Autowired
    private Faker faker;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setup() {
        cleanDatabase.clean();
    }

    @Test
    @Transactional
    public void findAllWithPredicateOfNameAndEmail() {
        List<Contact> contacts = contactFactory.create(5);
        Contact contact = contacts.get(faker.random().nextInt(contacts.size()));

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> query = builder.createQuery(Contact.class);

        Root<Contact> from = query.from(Contact.class);

        CriteriaQuery<Contact> criteriaQuery = query.select(from)
                .where(builder.and(
                        builder.like(from.get("name"), "%" + contact.getName() + "%"),
                        builder.equal(from.get("email"), contact.getEmail())));

        TypedQuery<Contact> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Contact> all = typedQuery.getResultList();
        Assertions.assertThat(all)
                .hasSize(1);
        Assertions.assertThat(all.get(0))
                .isEqualTo(contact);
    }

    @Test
    public void findAllWithResultWrapper() {
        List<Contact> contacts = contactFactory.create(5);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ContactWrapper> query = builder.createQuery(ContactWrapper.class);

        Root<Contact> from = query.from(Contact.class);
        CriteriaQuery<ContactWrapper> criteriaQuery = query.select(builder.construct(ContactWrapper.class,
                from.get("name"),
                from.get("email")))
                .where(builder.equal(from.get("id"), 1L));

        TypedQuery<ContactWrapper> typedQuery = entityManager.createQuery(criteriaQuery);
        ContactWrapper result = typedQuery.getSingleResult();

        Contact contact = contacts.get(0);

        Assertions.assertThat(result)
                .isNotNull();
        Assertions.assertThat(result.getName())
                .isEqualTo(contact.getName());
        Assertions.assertThat(result.getEmail())
                .isEqualTo(contact.getEmail());
    }

    @Test
    public void multiSelect() {
        List<Contact> contacts = contactFactory.create(5);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();

        Root<Contact> from = query.from(Contact.class);

        query.multiselect(
                from.get("name").alias("contact_name"),
                from.get("email").alias("contact_email"));
        CriteriaQuery<Tuple> criteriaQuery = query.where(builder.equal(from.get("id"), 1L));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        Tuple result = typedQuery.getSingleResult();

        Contact contact = contacts.get(0);

        Assertions.assertThat(result)
                .isNotNull();
        Assertions.assertThat(result.get("contact_name", String.class))
                .isEqualTo(contact.getName());
        Assertions.assertThat(result.get("contact_email", String.class))
                .isEqualTo(contact.getEmail());
    }
}

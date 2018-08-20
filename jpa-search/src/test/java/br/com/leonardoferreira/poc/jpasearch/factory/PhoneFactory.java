package br.com.leonardoferreira.poc.jpasearch.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import br.com.leonardoferreira.poc.jpasearch.domain.Phone;
import br.com.leonardoferreira.poc.jpasearch.repository.PhoneRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhoneFactory extends JBacon<Phone> {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private Faker faker;

    @Override
    protected Phone getDefault() {
        Phone phone = new Phone();

        phone.setNumber(faker.phoneNumber().phoneNumber());

        return phone;
    }

    @Override
    protected Phone getEmpty() {
        return new Phone();
    }

    @Override
    protected void persist(Phone phone) {
        phoneRepository.save(phone);
    }
}

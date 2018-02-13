package br.com.leonardoferreira.test.service;

import br.com.leonardoferreira.test.domain.Contact;
import br.com.leonardoferreira.test.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Transactional(readOnly = true)
    public List<Contact> findAll() {
        log.info("Method=findAll");
        return (List<Contact>) contactRepository.findAll();
    }

    public void create(Contact contact) {
        log.info("Method=create, contact={}", contact);
        contactRepository.save(contact);
    }

}

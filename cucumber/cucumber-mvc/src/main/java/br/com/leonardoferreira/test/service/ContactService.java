package br.com.leonardoferreira.test.service;

import br.com.leonardoferreira.test.domain.Contact;
import br.com.leonardoferreira.test.exception.ContactNotFound;
import br.com.leonardoferreira.test.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
public class ContactService extends ApplicationService {

    @Autowired
    private ContactRepository contactRepository;

    @Transactional(readOnly = true)
    public List<Contact> findAll() {
        log.info("Method=findAll");
        return (List<Contact>) contactRepository.findAll();
    }

    @Transactional
    public void create(Contact contact) {
        log.info("Method=create, contact={}", contact);
        contact.setAnswerableUser(currentUser().getUsername());
        contactRepository.save(contact);
    }

    @Transactional(readOnly = true)
    public Contact findById(Long id) {
        log.info("Method=findById, id={}", id);
        return contactRepository.findById(id)
                .orElseThrow(ContactNotFound::new);
    }

    @Transactional
    public void update(Contact contact) {
        log.info("Method=update, contact={}", contact);
        contact.setAnswerableUser(currentUser().getUsername());
        contactRepository.save(contact);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Method=delete, id={}", id);
        Contact contact = contactRepository.findById(id)
                .orElseThrow(ContactNotFound::new);
        contactRepository.delete(contact);
    }
}

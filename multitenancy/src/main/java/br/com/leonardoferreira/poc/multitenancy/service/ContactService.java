package br.com.leonardoferreira.poc.multitenancy.service;

import br.com.leonardoferreira.poc.multitenancy.domain.Contact;
import br.com.leonardoferreira.poc.multitenancy.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.multitenancy.domain.response.ContactResponse;
import br.com.leonardoferreira.poc.multitenancy.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.multitenancy.mapper.ContactMapper;
import br.com.leonardoferreira.poc.multitenancy.repository.ContactRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> findAll() {
        log.info("Method=findAll");

        List<Contact> contacts = (List<Contact>) contactRepository.findAll();
        return contactMapper.contactToResponse(contacts);
    }

    @Transactional(readOnly = true)
    public ContactResponse findById(Long id) {
        log.info("Method=findById, id={}", id);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return contactMapper.contactToResponse(contact);
    }

    @Transactional
    public Long create(ContactRequest contactRequest) {
        log.info("Method=create, contactRequest={}", contactRequest);

        Contact contact = contactMapper.contactRequestToContact(contactRequest);
        contactRepository.save(contact);

        return contact.getId();
    }

    @Transactional
    public void update(Long id, ContactRequest contactRequest) {
        log.info("Method=update, id={}, contactRequest={}", id, contactRequest);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        contactMapper.updateContactFromRequest(contact, contactRequest);

        contactRepository.save(contact);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Method=delete, id={}", id);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        contactRepository.delete(contact);
    }


}
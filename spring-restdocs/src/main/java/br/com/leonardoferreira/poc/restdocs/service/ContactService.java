package br.com.leonardoferreira.poc.restdocs.service;

import br.com.leonardoferreira.poc.restdocs.domain.Contact;
import br.com.leonardoferreira.poc.restdocs.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.restdocs.domain.response.ContactResponse;
import br.com.leonardoferreira.poc.restdocs.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.restdocs.mapper.ContactMapper;
import br.com.leonardoferreira.poc.restdocs.repository.ContactRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    public ContactService(final ContactRepository contactRepository,
                          final ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> findAll() {
        log.info("Method=findAll");

        List<Contact> contacts = contactRepository.findAll();
        return contactMapper.contactToResponse(contacts);
    }

    @Transactional(readOnly = true)
    public ContactResponse findById(final Long id) {
        log.info("Method=findById, id={}", id);

        return contactRepository.findById(id)
                .map(contactMapper::contactToResponse)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public Long create(final ContactRequest contactRequest) {
        log.info("Method=create, contactRequest={}", contactRequest);

        Contact contact = contactMapper.contactRequestToContact(contactRequest);
        contactRepository.save(contact);

        return contact.getId();
    }

    @Transactional
    public void update(final Long id, final ContactRequest contactRequest) {
        log.info("Method=update, id={}, contactRequest={}", id, contactRequest);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        contactMapper.updateContactFromRequest(contact, contactRequest);

        contactRepository.save(contact);
    }

    @Transactional
    public void delete(final Long id) {
        log.info("Method=delete, id={}", id);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        contactRepository.delete(contact);
    }


}

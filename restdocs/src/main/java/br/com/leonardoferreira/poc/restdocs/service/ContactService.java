package br.com.leonardoferreira.poc.restdocs.service;

import br.com.leonardoferreira.poc.restdocs.domain.Contact;
import br.com.leonardoferreira.poc.restdocs.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.restdocs.domain.response.ContactResponse;
import br.com.leonardoferreira.poc.restdocs.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.restdocs.mapper.ContactMapper;
import br.com.leonardoferreira.poc.restdocs.repository.ContactRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    public List<ContactResponse> findAll() {
        log.info("Method=findAll");

        List<Contact> contacts = (List<Contact>) contactRepository.findAll();
        return contactMapper.contactToResponse(contacts);
    }

    public ContactResponse findById(Long id) {
        log.info("Method=findById, id={}", id);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return contactMapper.contactToResponse(contact);
    }

    public Long create(ContactRequest contactRequest) {
        log.info("Method=create, contactRequest={}", contactRequest);

        Contact contact = contactMapper.contactRequestToContact(contactRequest);
        contactRepository.save(contact);

        return contact.getId();
    }

    public void update(Long id, ContactRequest contactRequest) {
        log.info("Method=update, id={}, contactRequest={}", id, contactRequest);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        contactMapper.updateContactFromRequest(contact, contactRequest);

        contactRepository.save(contact);
    }

    public void delete(Long id) {
        log.info("Method=delete, id={}", id);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        contactRepository.delete(contact);
    }


}

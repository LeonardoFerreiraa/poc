package br.com.leonardoferreira.poc.servicecall.service;

import br.com.leonardoferreira.poc.servicecall.adapter.ResponseAdapter;
import br.com.leonardoferreira.poc.servicecall.domain.Contact;
import br.com.leonardoferreira.poc.servicecall.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.servicecall.domain.response.ContactResponse;
import br.com.leonardoferreira.poc.servicecall.exception.ResourceNotFound;
import br.com.leonardoferreira.poc.servicecall.mapper.ContactMapper;
import br.com.leonardoferreira.poc.servicecall.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Transactional(readOnly = true)
    public List<ContactResponse> findAll() {
        List<Contact> contacts = contactRepository.findAll();
        return contactMapper.responsesFromContacts(contacts);
    }

    @Transactional(readOnly = true)
    public ContactResponse findById(Long id) {
        return findById(id, obj -> contactMapper.responseFromContact(obj));
    }

    @Transactional(readOnly = true)
    public <T> T findById(Long id, ResponseAdapter<T, Contact> adapter) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFound::new);

        return adapter.adapt(contact);
    }

    @Transactional
    public Long create(ContactRequest contactRequest) {
        Contact contact = contactMapper.contactFromRequest(contactRequest);
        contactRepository.save(contact);
        return contact.getId();
    }

}

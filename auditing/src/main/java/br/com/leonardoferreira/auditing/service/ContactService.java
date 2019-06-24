package br.com.leonardoferreira.auditing.service;

import br.com.leonardoferreira.auditing.domain.entity.Contact;
import br.com.leonardoferreira.auditing.domain.request.ContactRequest;
import br.com.leonardoferreira.auditing.domain.response.ContactResponse;
import br.com.leonardoferreira.auditing.exception.ResourceNotFoundException;
import br.com.leonardoferreira.auditing.repository.ContactRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> findAll() {
        return contactRepository.findAll().stream()
                .map(Contact::toContactResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContactResponse findById(final Long id) {
        return contactRepository.findById(1L)
                .map(Contact::toContactResponse)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public Long create(final ContactRequest contactRequest) {
        return contactRepository.save(contactRequest.toContact())
                .getId();
    }

    @Transactional
    public void update(final Long id, final ContactRequest contactRequest) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        contact.updateFromRequest(contactRequest);

        contactRepository.save(contact);
    }

    @Transactional
    public void delete(final Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        contactRepository.delete(contact);
    }

}

package br.com.leonardoferreira.poc.service.impl;

import java.util.List;

import br.com.leonardoferreira.poc.domain.entity.Contact;
import br.com.leonardoferreira.poc.domain.response.ContactResponse;
import br.com.leonardoferreira.poc.mapper.ContactMapper;
import br.com.leonardoferreira.poc.repository.ContactRepository;
import br.com.leonardoferreira.poc.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    @Override
    public List<ContactResponse> findAll() {
        final List<Contact> contacts = contactRepository.findAll();
        return contactMapper.fromContact(contacts);
    }

}

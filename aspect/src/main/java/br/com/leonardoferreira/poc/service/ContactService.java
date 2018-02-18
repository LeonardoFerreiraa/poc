package br.com.leonardoferreira.poc.service;

import br.com.leonardoferreira.poc.aspect.annotation.BenchMark;
import br.com.leonardoferreira.poc.domain.Contact;
import br.com.leonardoferreira.poc.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @BenchMark
    public List<Contact> findAll(Contact contact) {
        return contactRepository.findByName(contact.getName());
    }

    @BenchMark
    public void create(Contact contact) {
        contactRepository.save(contact);
    }
}

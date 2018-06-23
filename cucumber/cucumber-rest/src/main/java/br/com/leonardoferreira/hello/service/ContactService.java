package br.com.leonardoferreira.hello.service;

import br.com.leonardoferreira.hello.domain.Contact;
import br.com.leonardoferreira.hello.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lferreira on 6/30/17.
 */
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> findAll() {
        return (List<Contact>) contactRepository.findAll();
    }

    public Contact findOne(Long id) {
        return contactRepository.findById(id)
                .orElse(null);
    }

    public void save(Contact contact) {
        contactRepository.save(contact);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

}

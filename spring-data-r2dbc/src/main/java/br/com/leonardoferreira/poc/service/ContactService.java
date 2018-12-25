package br.com.leonardoferreira.poc.service;

import br.com.leonardoferreira.poc.domain.Contact;
import br.com.leonardoferreira.poc.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Flux<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Flux<Contact> findAll(int pageSize) {
         return contactRepository.count()
                .flatMapMany(count -> Flux.range(1, (int) Math.ceil((double) count / pageSize)))
                .concatMap(currentPage -> contactRepository.findAll(pageSize, (currentPage - 1) * pageSize));
    }

    public Mono<Contact> findById(Long id) {
        return contactRepository.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException::new));
    }

    public Mono<Long> insert(ContactRequest request) {
        Contact contact = new Contact(request.getName(), request.getEmail());

        return contactRepository.save(contact)
                .map(Contact::getId);
    }

    public Mono<Void> update(Long id, ContactRequest request) {
        return findById(id)
                .flatMap(contact -> contactRepository
                        .save(contact
                                .setEmail(request.getEmail())
                                .setName(request.getName()))
                        .then());
    }

    public Mono<Void> delete(Long id) {
        return findById(id)
                .flatMap(contact -> contactRepository.delete(contact));
    }

}

package br.com.leonardoferreira.poc.mapper;

import java.util.List;
import java.util.stream.Collectors;

import br.com.leonardoferreira.poc.domain.entity.Contact;
import br.com.leonardoferreira.poc.domain.response.ContactResponse;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public List<ContactResponse> fromContact(final List<Contact> contacts) {
        return contacts.stream()
                .map(this::fromContact)
                .collect(Collectors.toList());
    }

    public ContactResponse fromContact(final Contact contact) {
        return ContactResponse.builder()
                .id(contact.getId().toString())
                .name(contact.getName())
                .email(contact.getEmail())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .build();
    }
}

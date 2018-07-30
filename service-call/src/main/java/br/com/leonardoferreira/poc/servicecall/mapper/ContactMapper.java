package br.com.leonardoferreira.poc.servicecall.mapper;

import br.com.leonardoferreira.poc.servicecall.domain.Contact;
import br.com.leonardoferreira.poc.servicecall.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.servicecall.domain.response.ContactResponse;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = PhoneMapper.class)
public interface ContactMapper {

    @Mappings({
            @Mapping(target = "id",        ignore = true),
            @Mapping(target = "phones",    ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
    })
    Contact contactFromRequest(ContactRequest contactRequest);

    @Named("responseFromContact")
    @Mappings({
            @Mapping(target = "fullName",  source = "name"),
            @Mapping(target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm"),
            @Mapping(target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm"),
            @Mapping(target = "phones",    qualifiedByName = "responseFromPhoneWithoutContact")
    })
    ContactResponse responseFromContact(Contact contact);

    @IterableMapping(qualifiedByName = "responseFromContact")
    List<ContactResponse> responsesFromContacts(List<Contact> contacts);

    @Mapping(target = "phones", ignore = true)
    @Named("responseFromContactWithoutPhones")
    @InheritConfiguration(name = "responseFromContact")
    ContactResponse responseFromContactWithoutPhones(Contact contact);
}

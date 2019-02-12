package br.com.leonardoferreira.poc.multitenancy.mapper;

import br.com.leonardoferreira.poc.multitenancy.domain.Contact;
import br.com.leonardoferreira.poc.multitenancy.domain.request.ContactRequest;
import br.com.leonardoferreira.poc.multitenancy.domain.response.ContactResponse;
import java.util.List;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mappings({ // @formatter:off
            @Mapping(target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm"),
            @Mapping(target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm")
    }) // @formatter:on
    ContactResponse contactToResponse(Contact contact);


    List<ContactResponse> contactToResponse(List<Contact> contacts);

    @Mappings({ // @formatter:off
            @Mapping(target = "id",         ignore = true),
            @Mapping(target = "createdAt",  ignore = true),
            @Mapping(target = "updatedAt",  ignore = true),
            @Mapping(target = "owner",      ignore = true)
    }) // @formatter:off
    Contact contactRequestToContact(ContactRequest contactRequest);

    @InheritConfiguration
    void updateContactFromRequest(@MappingTarget Contact contact, ContactRequest contactRequest);

}
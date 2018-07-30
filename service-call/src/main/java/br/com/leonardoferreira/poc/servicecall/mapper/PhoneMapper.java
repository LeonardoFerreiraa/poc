package br.com.leonardoferreira.poc.servicecall.mapper;

import br.com.leonardoferreira.poc.servicecall.domain.Phone;
import br.com.leonardoferreira.poc.servicecall.domain.request.PhoneRequest;
import br.com.leonardoferreira.poc.servicecall.domain.response.PhoneResponse;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = ContactMapper.class)
public interface PhoneMapper {

    @Mappings({
            @Mapping(target = "id",         ignore = true),
            @Mapping(target = "createdAt",  ignore = true),
            @Mapping(target = "updatedAt",  ignore = true),
            @Mapping(target = "contact.id", source = "contact")
    })
    Phone phoneFromRequest(PhoneRequest phoneRequest);

    @Named("responseFromPhone")
    @Mapping(target = "contact", qualifiedByName = "responseFromContactWithoutPhones")
    PhoneResponse responseFromPhone(Phone phone);

    @InheritConfiguration
    @Named("responseFromPhoneWithoutContact")
    @Mapping(target = "contact", ignore = true)
    PhoneResponse responseFromPhoneWithoutContact(Phone phone);

    @IterableMapping(qualifiedByName = "responseFromPhone")
    List<PhoneResponse> responsesFromPhones(List<Phone> phones);
}

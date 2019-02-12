package br.com.leonardoferreira.poc.multitenancy.mapper;

import br.com.leonardoferreira.poc.multitenancy.domain.Account;
import br.com.leonardoferreira.poc.multitenancy.domain.request.CreateAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mappings({ // @formatter:off
            @Mapping(target = "id",          ignore = true),
            @Mapping(target = "createdAt",   ignore = true),
            @Mapping(target = "updatedAt",   ignore = true),
            @Mapping(target = "authorities", ignore = true)
    }) // @formatter:on
    Account accountFromCreateAccountRequest(CreateAccountRequest createAccountRequest);

}

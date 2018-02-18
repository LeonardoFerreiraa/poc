package br.com.leonardoferreira.poc.mapstruct.mapper;

import br.com.leonardoferreira.poc.mapstruct.domain.dto.UserDTO;
import br.com.leonardoferreira.poc.mapstruct.domain.entity.User;
import br.com.leonardoferreira.poc.mapstruct.mapper.converter.TitleTranslator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(uses = { TitleTranslator.class })
public interface UserMapper {

    @Mappings({
            @Mapping(target = "fullname", expression = "java(user.getFirstname() + \" \" + user.getLastname())"),
            @Mapping(target = "title", source = "title.id"),
            @Mapping(target = "address", defaultValue = "N/A"),
            @Mapping(target = "otherAttribute", ignore = true),
            @Mapping(target = "titleDescription", source = "title", qualifiedByName = { "TitleTranslator", "translateTitleENToPT" }),
            @Mapping(target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm"),
            @Mapping(target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm")
    })
    UserDTO userToUserDTO(User user);

    List<UserDTO> usersToUserDTOS(List<User> user);

    default Page<UserDTO> usersToUserDTOS(Page<User> users) {
        List<UserDTO> userDTOS = usersToUserDTOS(users.getContent());
        return new PageImpl<>(userDTOS, users.getPageable(), users.getTotalElements());
    }
}

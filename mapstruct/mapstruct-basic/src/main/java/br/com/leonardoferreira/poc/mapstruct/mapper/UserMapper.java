package br.com.leonardoferreira.poc.mapstruct.mapper;

import br.com.leonardoferreira.poc.mapstruct.domain.entity.User;
import br.com.leonardoferreira.poc.mapstruct.domain.response.UserResponse;
import br.com.leonardoferreira.poc.mapstruct.mapper.converter.TitleTranslator;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(
        uses = {
                TitleTranslator.class
        },
        componentModel = "spring"
)
public interface UserMapper {

    @Mappings({ // @formatter:off
            @Mapping(target = "fullName",          expression = "java(user.getFirstName() + \" \" + user.getLastName())"),
            @Mapping(target = "title",             source = "title.id"),
            @Mapping(target = "address",           defaultValue = "N/A"),
            @Mapping(target = "otherAttribute",    ignore = true),
            @Mapping(target = "titleDescription",  source = "title", qualifiedByName = {"TitleTranslator", "translateTitleENToPT"}),
            @Mapping(target = "createdAt",         dateFormat = "dd/MM/yyyy HH:mm:ss"),
            @Mapping(target = "updatedAt",         dateFormat = "dd/MM/yyyy HH:mm:ss")
    }) // @formatter:on
    UserResponse userToUserResponse(User user);

    List<UserResponse> usersToUserResponse(List<User> user);

    default Page<UserResponse> usersToUserResponse(final Page<User> users) {
        List<UserResponse> userResponses = usersToUserResponse(users.getContent());
        return new PageImpl<>(userResponses, users.getPageable(), users.getTotalElements());
    }

}

package br.com.leonardoferreira.poc.mapstruct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    private String fullname;

    private Long title;

    private String titleDescription;

    private String address;

    private String otherAttribute;

    private String createdAt;

    private String updatedAt;

}

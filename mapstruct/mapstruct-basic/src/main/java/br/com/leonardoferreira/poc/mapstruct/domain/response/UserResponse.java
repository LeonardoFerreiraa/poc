package br.com.leonardoferreira.poc.mapstruct.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;

    private String fullName;

    private Long title;

    private String titleDescription;

    private String address;

    private String otherAttribute;

    private String createdAt;

    private String updatedAt;

}

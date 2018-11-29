package br.com.leonardoferreira.poc.apiversion.domain.response.v2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contact {

    private Long id;

    private String fullName;

    private String email;

}

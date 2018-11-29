package br.com.leonardoferreira.poc.apiversion.domain;

import br.com.leonardoferreira.poc.apiversion.versions.ContactVersion;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contact {

    @JsonView(ContactVersion.V1.class)
    private Long id;

    @JsonView(ContactVersion.V1.class)
    private String name;

}

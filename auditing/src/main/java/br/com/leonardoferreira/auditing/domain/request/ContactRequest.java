package br.com.leonardoferreira.auditing.domain.request;

import br.com.leonardoferreira.auditing.domain.entity.Contact;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    public Contact toContact() {
        return Contact.builder()
                .name(name)
                .email(email)
                .build();
    }

}

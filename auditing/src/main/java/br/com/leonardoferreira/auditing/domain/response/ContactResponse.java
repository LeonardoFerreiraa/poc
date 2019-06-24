package br.com.leonardoferreira.auditing.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactResponse {

    private Long id;

    private String name;

    private String email;

    private String createdAt;

    private String updatedAt;

}

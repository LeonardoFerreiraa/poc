package br.com.leonardoferreira.poc.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Contact {

    @Id
    private String id;

    private String name;

    private String email;
}

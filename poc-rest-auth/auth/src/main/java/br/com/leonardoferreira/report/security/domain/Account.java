package br.com.leonardoferreira.report.security.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String accessToken;
}

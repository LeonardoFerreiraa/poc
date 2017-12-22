package br.com.leonardoferreira.hello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

/**
 * Created by lferreira on 6/30/17.
 */
@Data
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
public class Contact extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio.")
    private String name;

    @NotEmpty(message = "Email não pode ser vazio")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
        message = "Email deve ser um endereço de email valido.")
    private String email;

    private String phone;
}

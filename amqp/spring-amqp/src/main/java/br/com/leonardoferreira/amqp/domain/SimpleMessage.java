package br.com.leonardoferreira.amqp.domain;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SimpleMessage {

    @NotBlank
    private String message;

}

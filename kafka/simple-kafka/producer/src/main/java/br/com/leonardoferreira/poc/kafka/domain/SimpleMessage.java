package br.com.leonardoferreira.poc.kafka.domain;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SimpleMessage {

    @NotBlank
    private String str;

}

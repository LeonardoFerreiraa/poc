package br.com.leonardoferreira.poc.webclient.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ComplexThing {

    private String first;

    private String second;

    private Long calculatedData;

    private LocalDateTime creation;

}

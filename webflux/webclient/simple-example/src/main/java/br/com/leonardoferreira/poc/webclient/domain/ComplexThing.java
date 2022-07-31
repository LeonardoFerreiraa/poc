package br.com.leonardoferreira.poc.webclient.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplexThing {

    private String first;

    private String second;

    private Long calculatedData;

    private LocalDateTime creation;

}

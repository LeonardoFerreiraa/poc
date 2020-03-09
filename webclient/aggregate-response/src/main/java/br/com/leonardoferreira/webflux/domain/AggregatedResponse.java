package br.com.leonardoferreira.webflux.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AggregatedResponse {

    private final String todoTitle;

    private final String uuid;

}

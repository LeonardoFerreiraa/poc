package br.com.leonardoferreira.webflux.domain;

import lombok.Data;

@Data
public class TodoResponse {

    private Long id;

    private Long userId;

    private String title;

    private Boolean completed;

}

package br.com.leonardoferreira.poc.mapstruct.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public enum TitleEnum {

    DOCTOR(1L, "DOCTOR"),
    MASTER(2L, "MASTER");

    private Long id;

    private String title;

}

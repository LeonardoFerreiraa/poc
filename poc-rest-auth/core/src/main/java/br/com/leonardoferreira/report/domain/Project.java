package br.com.leonardoferreira.report.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Project extends BaseDomain {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}

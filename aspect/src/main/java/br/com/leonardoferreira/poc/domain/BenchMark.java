package br.com.leonardoferreira.poc.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class BenchMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String methodName;

    private Long executionTime;

}

package br.com.leonardoferreira.poc.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class BenchMark {
    private String id;
    private String methodName;
    private Long executionTime;
}

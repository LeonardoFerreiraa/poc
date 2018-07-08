package br.com.leonardoferreira.poc.batch;

import lombok.Data;

@Data
public class BatchImportItem {

    private Long externalId;

    private String customStr;

    private String customIgnoredProperty;

    private String customIgnoredProperty2;

}

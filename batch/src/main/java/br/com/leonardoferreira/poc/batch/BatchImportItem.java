package br.com.leonardoferreira.poc.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchImportItem {

    private Long externalId;

    private String customStr;

    private String customIgnoredProperty;

    private String customIgnoredProperty2;

}

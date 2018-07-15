package br.com.leonardoferreira.poc.batch;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
public class BatchImportReader implements ItemReader<BatchImportItem>, InitializingBean {

    @Value("#{jobParameters[fileName]}")
    private String fileName;

    private CSVParser csvParser;

    private Iterator<CSVRecord> it;

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource resource = new FileSystemResource(fileName);
        Reader reader = Files.newBufferedReader(Paths.get(resource.getURI()));

        CSVFormat format = CSVFormat.DEFAULT
                .withHeader("externalId", "customStr", "customIgnoredProperty", "customIgnoredProperty2")
                .withFirstRecordAsHeader();

        csvParser = new CSVParser(reader, format);
        it = csvParser.getRecords().iterator();
    }

    @Override
    public BatchImportItem read() throws Exception {
        if (it.hasNext()) {
            CSVRecord next = it.next();
            return BatchImportItem.builder()
                    .externalId(Long.parseLong(next.get("externalId")))
                    .customStr(next.get("customStr"))
                    .customIgnoredProperty(next.get("customIgnoredProperty"))
                    .customIgnoredProperty2(next.get("customIgnoredProperty2"))
                    .build();
        }

        try {
            csvParser.close();
        } catch (IOException e) {
            log.info("Method=read, E=", e.getMessage());
        }
        return null;
    }
}

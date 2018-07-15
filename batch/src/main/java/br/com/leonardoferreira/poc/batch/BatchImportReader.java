package br.com.leonardoferreira.poc.batch;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

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
        Reader reader = Files.newBufferedReader(Paths.get(new FileSystemResource(fileName).getURI()));

        CSVFormat format = CSVFormat.DEFAULT
                .withHeader("externalId", "customStr", "customIgnoredProperty", "customIgnoredProperty2")
                .withFirstRecordAsHeader();

        csvParser = new CSVParser(reader, format);
        it = csvParser.iterator();
    }

    @Override
    public BatchImportItem read() throws Exception {
        CSVRecord next = getNext();
        if (next != null) {
            return BatchImportItem.builder()
                    .externalId(Long.parseLong(next.get("externalId")))
                    .customStr(next.get("customStr"))
                    .customIgnoredProperty(next.get("customIgnoredProperty"))
                    .customIgnoredProperty2(next.get("customIgnoredProperty2"))
                    .build();
        }

        csvParser.close();
        return null;
    }

    private synchronized CSVRecord getNext() {
        return it.hasNext() ? it.next() : null;
    }
}

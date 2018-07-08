package br.com.leonardoferreira.poc.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class BatchImportReader extends FlatFileItemReader<BatchImportItem> implements InitializingBean {

    @Value("#{jobParameters[fileName]}")
    private String fileName;

    @Override
    public void afterPropertiesSet() throws Exception {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("externalId", "customStr",
                "customIgnoredProperty", "customIgnoredProperty2");

        BeanWrapperFieldSetMapper<BatchImportItem> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BatchImportItem.class);

        DefaultLineMapper<BatchImportItem> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        setLineMapper(lineMapper);
        setResource(new FileSystemResource(fileName));
        setLinesToSkip(1);
    }
}

package br.com.leonardoferreira.poc.batch;

import br.com.leonardoferreira.poc.domain.Item;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchImportConfig {

    private static final String BATCH_IMPORT_STEP = "batchImportStep";

    private static final String BATCH_IMPORT_TASK_EXECUTOR = "batchImportTaskExecutor";

    public static final String BATCH_IMPORT_JOB = "batchImportJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean(BATCH_IMPORT_JOB)
    public Job batchImportJob(@Qualifier(BATCH_IMPORT_STEP) Step step) {
        return jobBuilderFactory.get(BATCH_IMPORT_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean(BATCH_IMPORT_STEP)
    public Step batchImportStep(BatchImportReader batchImportReader,
                                BatchImportProcessor batchImportProcessor,
                                BatchImportWriter batchImportWriter,
                                @Qualifier(BATCH_IMPORT_TASK_EXECUTOR) TaskExecutor taskExecutor) {
        return stepBuilderFactory.get(BATCH_IMPORT_STEP)
                .<BatchImportItem, Item>chunk(200)
                .reader(batchImportReader)
                .processor(batchImportProcessor)
                .writer(batchImportWriter)
                .taskExecutor(taskExecutor)
                .throttleLimit(20)
                .build();
    }

    @Bean(BATCH_IMPORT_TASK_EXECUTOR)
    public TaskExecutor batchImportTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(20);

        return threadPoolExecutor;
    }
}

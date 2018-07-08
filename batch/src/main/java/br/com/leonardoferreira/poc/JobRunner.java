package br.com.leonardoferreira.poc;

import br.com.leonardoferreira.poc.batch.BatchImportConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
//@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(BatchImportConfig.BATCH_IMPORT_JOB)
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long start = System.currentTimeMillis();
        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .addString("fileName", "/home/lferreira/bla.csv")
                .toJobParameters();
        jobLauncher.run(job, params);

        long end = System.currentTimeMillis();
        log.info("--- {}ms", end - start);
    }
}

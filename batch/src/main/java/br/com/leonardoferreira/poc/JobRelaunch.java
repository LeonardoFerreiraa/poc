package br.com.leonardoferreira.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Configuration
public class JobRelaunch implements ApplicationRunner {

    @Autowired
    private JobOperator jobOperator;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("jobnames = {}", jobOperator.getJobNames());
    }

    @Bean
    public JobOperator jobOperator(JobExplorer jobExplorer,
                                   JobLauncher jobLauncher,
                                   JobRegistry jobRegistry,
                                   JobRepository jobRepository) {
        SimpleJobOperator jobOperator = new SimpleJobOperator();
        jobOperator.setJobExplorer(jobExplorer);
        jobOperator.setJobLauncher(jobLauncher);
        jobOperator.setJobRegistry(jobRegistry);
        jobOperator.setJobRepository(jobRepository);
        return jobOperator;
    }
}

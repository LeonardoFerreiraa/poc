package br.com.leonardoferreira.poc.controller;

import br.com.leonardoferreira.poc.batch.BatchImportConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RestController
public class ApplicationController {

    private static final String FILE_PATH = "/tmp/bla.csv";

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(BatchImportConfig.BATCH_IMPORT_JOB)
    private Job job;

    @SneakyThrows
    @GetMapping("/create-data")
    public String createData() {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH));

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("externalId", "customStr", "customIgnoredProperty", "customIgnoredProperty2"));
        for (int i = 0; i < 500_000; i++) {
            csvPrinter.printRecord(i, "str-" + i, "customIgnoredProperty-" + i, "customIgnoredProperty2-" + i);
        }

        csvPrinter.flush();
        csvPrinter.close();

        return "ok";
    }

    @SneakyThrows
    @PostMapping("/run-job")
    public String runJob() {
        long start = System.currentTimeMillis();
        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .addString("fileName", FILE_PATH)
                .toJobParameters();
        jobLauncher.run(job, params);
        long end = System.currentTimeMillis();

        return (end - start) + "ms";
    }
}

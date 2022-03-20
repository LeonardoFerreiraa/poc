package br.com.leonardoferreira.poc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.LoggerFactory;

@Slf4j
public class BenchmarkApplication {

    public static void main(String[] args) throws Exception {
        final Options options = new OptionsBuilder()
                .forks(1)
                .warmupIterations(1)
                .threads(10)
                .output("result/%s.txt".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))))
                .build();

        new Runner(options)
                .run();

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();
    }

}

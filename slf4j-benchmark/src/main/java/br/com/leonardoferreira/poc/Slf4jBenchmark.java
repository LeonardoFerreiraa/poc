package br.com.leonardoferreira.poc;

import java.util.UUID;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@State(Scope.Benchmark)
@Measurement(iterations = 5, time = 3)
public class Slf4jBenchmark {

    private static final Logger console = LoggerFactory.getLogger("consolelogger");

    private static final Logger async = LoggerFactory.getLogger("asynclogger");

    private String uuid;

    @Setup(Level.Invocation)
    public void setup() {
        uuid = UUID.randomUUID().toString();
    }

    @Benchmark
    public void systemout() {
        System.out.println("system out " + uuid);
    }

    @Benchmark
    public void slf4jConsoleAppender() {
        console.info("slf4j-console {}", uuid);
    }

    @Benchmark
    public void slf4jAsyncAppender() {
        async.info("slf4j-async {}", uuid);
    }

}

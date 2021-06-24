package test

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.util.concurrent.TimeUnit

@Fork(1)
@State(Scope.Benchmark)
@Warmup(iterations = 2)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
class PasswordValidatorTestBenchmark {

    private val cases = listOf(
        "",
        "aa",
        "ab",
        "AAAbbbCc",
        "AbTp9!foo",
        "AbTp9!foA",
        "AbTp9 fok",
        "AbTp9!fok"
    )

    @Benchmark
    fun withRegexBenchmark(): Boolean {
        return RegexPasswordValidator.isValid(cases.random())
    }

    @Benchmark
    fun withManualBenchmark(): Boolean {
        return PasswordValidator.isValid(cases.random())
    }

}

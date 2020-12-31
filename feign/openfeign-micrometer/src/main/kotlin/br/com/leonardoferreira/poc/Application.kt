package br.com.leonardoferreira.poc

import feign.Feign
import feign.micrometer.MicrometerCapability
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.logging.LoggingMeterRegistry
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignBuilderCustomizer
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.Duration

@EnableFeignClients
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class MeterRegistryConfig {

    @Bean
    fun meterRegistry(): MeterRegistry =
        LoggingMeterRegistry()

}

@FeignClient(name = "githubClient", url = "https://api.github.com")
interface GithubClient {

    @GetMapping("/repos/{owner}/{repo}/contributors")
    fun findContributors(@PathVariable owner: String, @PathVariable repo: String): List<Contributor>

}

data class Contributor(val login: String, val contributions: Int)

@Configuration
class MicrometerFeignBuilderCustomizer(
    private val meterRegistry: MeterRegistry
) : FeignBuilderCustomizer {

    override fun customize(builder: Feign.Builder) {
        builder.addCapability(MicrometerCapability(meterRegistry))
    }

}

@Component
class Runner(
    private val githubClient: GithubClient
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        githubClient.findContributors("OpenFeign", "feign")
    }

}

package br.com.leonardoferreira.poc

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
@ConfigurationPropertiesScan
class PocApplication

fun main(args: Array<String>) {
    runApplication<PocApplication>(*args)
}

@Component
class Runner(
    private val rabbitPropertyMap: RabbitPropertyMap
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        println(rabbitPropertyMap)
    }

}

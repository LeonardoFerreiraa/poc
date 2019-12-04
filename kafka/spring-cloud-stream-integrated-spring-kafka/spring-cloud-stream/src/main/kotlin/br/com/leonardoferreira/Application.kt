package br.com.leonardoferreira

import br.com.leonardoferreira.binding.CreatedUserBinding
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@EnableBinding(CreatedUserBinding::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

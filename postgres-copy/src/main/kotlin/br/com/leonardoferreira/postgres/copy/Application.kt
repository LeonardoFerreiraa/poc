package br.com.leonardoferreira.postgres.copy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CopyApplication

fun main(args: Array<String>) {
    runApplication<CopyApplication>(*args)
}

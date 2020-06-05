package br.com.leonardoferreira.poc

import br.com.leonardoferreira.poc.verticle.HttpServerVerticle
import br.com.leonardoferreira.poc.verticle.JokeVerticle
import br.com.leonardoferreira.poc.verticle.MessageVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.core.deployVerticleAwait
import java.util.logging.Logger

private val log: Logger = Logger.getLogger("main")

suspend fun main() {
    val vertx: Vertx = Vertx.vertx()

    val messageApiVerticleId = vertx.deployVerticleAwait(
        HttpServerVerticle::class.java.name
    )
    log.info("M=main, I=deployed HttpServerVerticle, id=$messageApiVerticleId")

    val messageVerticleId = vertx.deployVerticleAwait(
        MessageVerticle::class.java.name
    )
    log.info("M=main, I=deployed MessageVerticle, id=$messageVerticleId")

    val jokeVerticleId = vertx.deployVerticleAwait(
        JokeVerticle::class.java.name
    )
    log.info("M=main, I=deployed JokeVerticle, id=$jokeVerticleId")
}

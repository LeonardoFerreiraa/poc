package br.com.leonardoferreira.poc.verticle

import br.com.leonardoferreira.poc.util.Outcome.Failure
import br.com.leonardoferreira.poc.util.Outcome.Success
import br.com.leonardoferreira.poc.util.catching
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.HttpRequest
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.predicate.ResponsePredicate
import io.vertx.ext.web.codec.BodyCodec
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.ext.web.client.sendAwait
import kotlinx.coroutines.launch

class JokeVerticle : CoroutineVerticle() {

    override suspend fun start() {
        val eventBus: EventBus = vertx.eventBus()
        eventBus.consumer(EVENT_BUS_ADDRESS, ::makeAJoke)
    }

    private fun makeAJoke(event: Message<Nothing>) {
        launch {
            val request: HttpRequest<JsonObject> = WebClient.create(vertx)
                .get(443, "httpbin.org", "/uuid")
                .ssl(true)
                .putHeader("Accept", "application/json")
                .`as`(BodyCodec.jsonObject())
                .expect(ResponsePredicate.SC_OK)

            when (val outcome = catching { request.sendAwait() }) {
                is Success ->
                    event.reply(
                        jsonObjectOf(
                            "joke" to outcome.result.body().getString("uuid")
                        )
                    )
                is Failure ->
                    event.fail(999, outcome.reason)
            }
        }
    }

    companion object {
        const val EVENT_BUS_ADDRESS = "JOKE_EVENT_BUS_ADDRESS"
    }

}

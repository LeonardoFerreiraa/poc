package br.com.leonardoferreira.poc.verticle

import io.vertx.core.Handler
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle

class MessageVerticle : CoroutineVerticle() {

    private val buildMessageHandler: Handler<Message<JsonObject>> = Handler { message ->
        val payload = message.body()
        val name = payload.getString("name")

        message.reply(
            jsonObjectOf(
                "message" to "hello $name"
            )
        )
    }

    override suspend fun start() {
        vertx.eventBus()
            .consumer(EVENT_BUS_ADDRESS, buildMessageHandler)
    }

    companion object {
        const val EVENT_BUS_ADDRESS = "EVENT_BUS_ADDRESS"
    }

}

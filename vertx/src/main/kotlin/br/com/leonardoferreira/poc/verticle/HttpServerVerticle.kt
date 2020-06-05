package br.com.leonardoferreira.poc.verticle

import br.com.leonardoferreira.poc.util.Outcome.Failure
import br.com.leonardoferreira.poc.util.Outcome.Success
import br.com.leonardoferreira.poc.util.catching
import io.vertx.core.Handler
import io.vertx.core.eventbus.Message
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.eventbus.requestAwait
import io.vertx.kotlin.core.http.endAwait
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Logger

class HttpServerVerticle : CoroutineVerticle() {

    private val log: Logger = Logger.getLogger(javaClass.name)

    override suspend fun start() {
        val router: Router = Router.router(vertx)
        router.route().handler(ContentTypeHeaderHandler)
        router.get("/messages").handler(::handleGreeting)
        router.get("/jokes").handler(::handleJoke)

        val httpServerOptions = HttpServerOptions()
        httpServerOptions.logActivity = true

        vertx.createHttpServer(httpServerOptions)
            .requestHandler(router)
            .listenAwait(8080)
    }

    private fun handleGreeting(routingContext: RoutingContext) {
        launch {
            val name: String = routingContext.queryParam("name").first()
            log.info("M=handleGreeting, name=$name")

            val messageBuilderResponse: Message<JsonObject> = vertx.eventBus()
                .requestAwait(
                    MessageVerticle.EVENT_BUS_ADDRESS,
                    jsonObjectOf(
                        "name" to name
                    )
                )

            val payload = messageBuilderResponse.body()

            routingContext.response()
                .setStatusCode(200)
                .endAwait(payload.toBuffer())
        }
    }

    private fun handleJoke(routingContext: RoutingContext) {
        launch {
            val outcome = catching {
                vertx.eventBus()
                    .requestAwait<JsonObject>(JokeVerticle.EVENT_BUS_ADDRESS, null)
            }

            when (outcome) {
                is Success -> routingContext.response()
                    .setStatusCode(200)
                    .endAwait(outcome.result.body().toBuffer())
                is Failure -> routingContext.response()
                    .setStatusCode(500)
                    .endAwait()
            }
        }
    }

    object ContentTypeHeaderHandler : Handler<RoutingContext> {
        override fun handle(event: RoutingContext) {
            val response = event.response()
            response.putHeader("Content-Type", "application/json")
            event.next()
        }
    }

}

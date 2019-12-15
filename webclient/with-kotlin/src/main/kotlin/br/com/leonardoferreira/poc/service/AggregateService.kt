package br.com.leonardoferreira.poc.service

import br.com.leonardoferreira.poc.domain.request.AnythingRequest
import br.com.leonardoferreira.poc.domain.response.AggregatedResponse
import br.com.leonardoferreira.poc.domain.response.AnythingResponse
import br.com.leonardoferreira.poc.domain.response.TodoResponse
import br.com.leonardoferreira.poc.domain.response.UuidResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@Service
class AggregateService(
    private val httpBinClient: WebClient,
    private val jsonPlaceHolderClient: WebClient
) {

    suspend fun call(): AggregatedResponse = coroutineScope {
        val todoAsync = async { retrieveTodo() }

        val uuidResponse = async { retrieveUuid() }

        AggregatedResponse(
            todoTitle = todoAsync.await().title,
            uuid = uuidResponse.await().uuid
        )
    }

    private suspend fun retrieveUuid(): UuidResponse =
        httpBinClient.get()
            .uri("/uuid")
            .awaitExchange()
            .awaitBody()

    private suspend fun retrieveTodo(): TodoResponse {
        val anythingResponse = httpBinClient.post()
            .uri("/anything")
            .bodyValue(AnythingRequest(200))
            .awaitExchange()
            .awaitBody<AnythingResponse>()

        return jsonPlaceHolderClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/todos/{id}")
                    .build(anythingResponse.json.number)
            }
            .awaitExchange()
            .awaitBody()
    }

}

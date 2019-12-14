package br.com.leonardoferreira.poc.service

import br.com.leonardoferreira.poc.domain.request.AnythingRequest
import br.com.leonardoferreira.poc.domain.response.AggregatedResponse
import br.com.leonardoferreira.poc.domain.response.AnythingResponse
import br.com.leonardoferreira.poc.domain.response.TodoResponse
import br.com.leonardoferreira.poc.domain.response.UuidResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@Service
class AggregateService(
    private val httpBinClient: WebClient,
    private val jsonPlaceHolderClient: WebClient
) {

    suspend fun aggregate(): AggregatedResponse {
        val todoAsync = retrieveTodoAsync()

        val uuidResponse = retrieveUuidAsync()

        return AggregatedResponse(
            todoTitle = todoAsync.await().title,
            uuid = uuidResponse.await().uuid
        )
    }

    private suspend fun retrieveUuidAsync(): Deferred<UuidResponse> = GlobalScope.async {
        httpBinClient.get()
            .uri("/uuid")
            .awaitExchange()
            .awaitBody<UuidResponse>()
    }

    private fun retrieveTodoAsync(): Deferred<TodoResponse> = GlobalScope.async {
        val anythingResponse = httpBinClient.post()
            .uri("/anything")
            .bodyValue(AnythingRequest(200))
            .awaitExchange()
            .awaitBody<AnythingResponse>()

        jsonPlaceHolderClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/todos/{id}")
                    .build(anythingResponse.json.number)
            }
            .awaitExchange()
            .awaitBody<TodoResponse>()
    }

}

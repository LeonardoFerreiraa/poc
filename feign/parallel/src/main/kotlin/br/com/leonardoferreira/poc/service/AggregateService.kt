package br.com.leonardoferreira.poc.service

import br.com.leonardoferreira.poc.client.HttpBinClient
import br.com.leonardoferreira.poc.client.TodoClient
import br.com.leonardoferreira.poc.domain.request.AnythingRequest
import br.com.leonardoferreira.poc.domain.response.AggregatedResponse
import org.springframework.stereotype.Service
import java.util.concurrent.Callable
import java.util.concurrent.Executors

@Service
class AggregateService(
    private val httpBinClient: HttpBinClient,
    private val todoClient: TodoClient
) {

    fun call(): AggregatedResponse {
        val threadPool = Executors.newCachedThreadPool()

        val todoAsync = threadPool.submit(Callable {
            val anythingResponse = httpBinClient.anything(AnythingRequest(200))

            todoClient.findById(anythingResponse.json.number)
        })

        val uuidAsync = threadPool.submit(Callable {
            httpBinClient.uuid()
        })

        return AggregatedResponse(
            todoTitle = todoAsync.get().title,
            uuid = uuidAsync.get().uuid
        )
    }

}

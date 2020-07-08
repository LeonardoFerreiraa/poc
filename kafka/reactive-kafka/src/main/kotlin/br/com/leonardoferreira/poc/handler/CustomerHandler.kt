package br.com.leonardoferreira.poc.handler

import br.com.leonardoferreira.poc.domain.CustomerRequest
import br.com.leonardoferreira.poc.service.CustomerService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class CustomerHandler(
    private val customerService: CustomerService
) {

    suspend fun handleCreate(serverRequest: ServerRequest): ServerResponse {
        val customerRequest = serverRequest.awaitBody<CustomerRequest>()
        customerService.create(customerRequest)

        return ServerResponse.accepted()
            .buildAndAwait()
    }

}

package br.com.leonardoferreira.poc.router

import br.com.leonardoferreira.poc.handler.CustomerHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class CustomerRouter {

    @Bean
    fun customerRouterFunction(customerHandler: CustomerHandler) = coRouter {
        POST("/customers", customerHandler::handleCreate)
    }

}

package br.com.leonardoferreira.poc.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {

    @Bean
    fun httpBinClient(): WebClient =
        WebClient.builder()
            .baseUrl("https://httpbin.org/")
            .build()

    @Bean
    fun jsonPlaceHolderClient(): WebClient =
        WebClient.builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()

}

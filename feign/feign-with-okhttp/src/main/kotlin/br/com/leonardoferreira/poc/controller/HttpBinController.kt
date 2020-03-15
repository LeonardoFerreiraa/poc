package br.com.leonardoferreira.poc.controller

import br.com.leonardoferreira.poc.client.HttpBinClient
import br.com.leonardoferreira.poc.domain.UuidResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/http-bin")
class HttpBinController(
    private val httpBinClient: HttpBinClient
) {

    @GetMapping("/uuid")
    fun findUuid(): UuidResponse =
        httpBinClient.findUuid()

    @GetMapping("/status/{status}")
    fun status(@PathVariable status: Int) =
        httpBinClient.status(status)

}
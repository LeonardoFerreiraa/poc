package br.com.leonardoferreira.poc.controller

import br.com.leonardoferreira.poc.client.HttpBinClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/uuid")
class UUIDController(
    private val httpBinClient: HttpBinClient
) {

    @GetMapping
    fun findUUID() =
        httpBinClient.findUUID().uuid

}

package br.com.leonardoferreira.poc.client

import br.com.leonardoferreira.poc.domain.UuidResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "httpBinClient", url = "https://httpbin.org/")
interface HttpBinClient {

    @GetMapping("/uuid")
    fun findUuid(): UuidResponse

    @GetMapping("/status/{status}")
    fun status(@PathVariable status: Int)

}
package br.com.leonardoferreira.poc.client

import br.com.leonardoferreira.poc.UUIDResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "httpBinClient", url = "\${feign.client.config.httpBinClient.url}")
interface HttpBinClient {

    @GetMapping("/uuid")
    fun findUUID(): UUIDResponse

} 

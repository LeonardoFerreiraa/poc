package br.com.leonardoferreira.poc.client

import br.com.leonardoferreira.poc.domain.request.AnythingRequest
import br.com.leonardoferreira.poc.domain.response.AnythingResponse
import br.com.leonardoferreira.poc.domain.response.UuidResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    name = "HttpBinClient",
    url = "http://httpbin.org"
)
interface HttpBinClient {

    @PostMapping("/anything")
    fun anything(anythingRequest: AnythingRequest): AnythingResponse

    @GetMapping("/uuid")
    fun uuid(): UuidResponse

}

package br.com.leonardoferreira.poc.client

import br.com.leonardoferreira.poc.domain.response.TodoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "todoClient",
    url = "https://jsonplaceholder.typicode.com/"
)
interface TodoClient {

    @GetMapping("/todos/{id}")
    fun findById(@PathVariable id: Long): TodoResponse

}

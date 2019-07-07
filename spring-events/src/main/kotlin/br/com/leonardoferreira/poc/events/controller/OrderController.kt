package br.com.leonardoferreira.poc.events.controller

import br.com.leonardoferreira.poc.events.domain.request.OrderRequest
import br.com.leonardoferreira.poc.events.domain.response.OrderResponse
import br.com.leonardoferreira.poc.events.service.OrderService
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): OrderResponse =
        orderService.findById(id)

    @PostMapping
    fun crete(@RequestBody orderRequest: OrderRequest): HttpEntity<*> {
        val id: Long = orderService.create(orderRequest)

        val location = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/orders/{id}")
            .build(id)

        return ResponseEntity.created(location).build<Any>()
    }

}

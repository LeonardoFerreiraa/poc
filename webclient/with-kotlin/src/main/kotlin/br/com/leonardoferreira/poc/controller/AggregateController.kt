package br.com.leonardoferreira.poc.controller

import br.com.leonardoferreira.poc.service.AggregateService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/aggregations")
class AggregateController(
    private val aggregateService: AggregateService
) {

    @GetMapping
    suspend fun retrieveAggregation() =
        aggregateService.call()

}

package br.com.leonardoferreira.poc.controller

import br.com.leonardoferreira.poc.service.CustomerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping
    fun index() =
        customerService.findAll()

}
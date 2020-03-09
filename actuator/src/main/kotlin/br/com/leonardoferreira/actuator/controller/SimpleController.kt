package br.com.leonardoferreira.actuator.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleController {

    @GetMapping("/greetings")
    fun greeting() =
        "hello world"

}

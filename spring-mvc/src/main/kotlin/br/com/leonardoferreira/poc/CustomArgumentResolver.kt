package br.com.leonardoferreira.poc

import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest

@RestController
@RequestMapping("/custom-arguments")
class CustomArgumentController {

    @GetMapping
    fun print(
        customRequestBody: CustomRequestBody,
        @RequestParam params: MultiValueMap<String, String>
    ) {
        println(customRequestBody)
        println(params)
    }

}

data class CustomRequestBody(
    val str: String,
    val int: Int
)

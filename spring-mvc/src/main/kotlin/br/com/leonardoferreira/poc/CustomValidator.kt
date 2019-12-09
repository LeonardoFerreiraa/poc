package br.com.leonardoferreira.poc

import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/simple-messages")
class SimpleController {

    @InitBinder
    fun initBinder(webDataBinder: WebDataBinder) {
        webDataBinder.addValidators(SimpleMessageValidator())
    }

    @PostMapping
    fun index(@Valid @RequestBody simpleMessage: SimpleMessage): HttpEntity<*> =
        ResponseEntity.created(URI.create("/simple-messages")).build<Any>()
}

data class SimpleMessage(val message: String)

class SimpleMessageValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean =
        SimpleMessage::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {
        ValidationUtils.rejectIfEmpty(errors, "message", "message.empty")
    }

}


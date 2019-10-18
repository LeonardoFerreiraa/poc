package br.com.leonardoferreira.poc.controller

import br.com.leonardoferreira.poc.domain.request.CreateContactRequest
import br.com.leonardoferreira.poc.service.ContactService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/contacts")
class ContactController(
    private val contactService: ContactService
) {

    @PostMapping
    fun create(
        @RequestBody createContactRequest: CreateContactRequest
    ): ResponseEntity<*> {
        val id = contactService.create(createContactRequest)

        val location = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/contacts/{id}")
            .build(id)

        return ResponseEntity.created(location).build<Any>()
    }

}

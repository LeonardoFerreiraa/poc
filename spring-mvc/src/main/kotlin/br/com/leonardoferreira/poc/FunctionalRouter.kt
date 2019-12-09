package br.com.leonardoferreira.poc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router
import java.util.concurrent.atomic.AtomicLong

@Configuration
class Router {

    @Bean
    fun contactRouter(contactHandler: ContactHandler) =
        router {
            GET("/contacts").invoke(contactHandler::findAll)
            GET("/contacts/{id}").invoke(contactHandler::findById)
            POST("/contacts").invoke(contactHandler::create)
        }

}

@Component
class ContactHandler(
    private val contactService: ContactService
) {

    fun findAll(serverRequest: ServerRequest) =
        ServerResponse.ok()
            .body(contactService.findAll())

    fun findById(serverRequest: ServerRequest): ServerResponse {
        val id = serverRequest.pathVariable("id").toLong()
        val contact = contactService.findById(id)

        return if (contact == null) {
            ServerResponse.notFound().build()
        } else {
            ServerResponse.ok().body(contact)
        }
    }

    fun create(serverRequest: ServerRequest): ServerResponse {
        val id = contactService.create(serverRequest.body())

        val location = serverRequest.uriBuilder()
            .path("/{id}")
            .build(id)

        return ServerResponse.created(location).build()
    }

}

@Service
class ContactService {
    private val counter = AtomicLong()

    private val contacts = mutableSetOf(
        Contact(counter.incrementAndGet(), "Contact 1", "contact1@email.com"),
        Contact(counter.incrementAndGet(), "Contact 2", "contact2@email.com"),
        Contact(counter.incrementAndGet(), "Contact 3", "contact3@email.com"),
        Contact(counter.incrementAndGet(), "Contact 4", "contact4@email.com"),
        Contact(counter.incrementAndGet(), "Contact 5", "contact5@email.com")
    )

    fun findAll(): Set<Contact> =
        contacts

    fun findById(id: Long): Contact? =
        contacts.find { it.id == id }

    fun create(createContactRequest: Contact): Long {
        val contact = createContactRequest.copy(
            id = counter.incrementAndGet()
        )

        contacts.add(contact)

        return contact.id
    }
}


data class Contact(
    val id: Long,
    val name: String,
    val email: String
)

package br.com.leonardoferreira.poc.listener

import br.com.leonardoferreira.poc.channel.CreatedContactChannel
import br.com.leonardoferreira.poc.domain.Contact
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Component

@Component
class CreatedContactListener {

    @StreamListener(CreatedContactChannel.CREATED_CONTACT_INPUT)
    fun createdContact(contact: Contact) {
        println("listenedContact=$contact")
    }

}

package br.com.leonardoferreira.poc.service

import br.com.leonardoferreira.poc.channel.CreatedContactChannel
import br.com.leonardoferreira.poc.domain.request.CreateContactRequest
import br.com.leonardoferreira.poc.mapper.toContact
import br.com.leonardoferreira.poc.repository.ContactRepository
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContactService(
    private val contactRepository: ContactRepository,
    private val contactChannel: CreatedContactChannel
) {

    @Transactional
    fun create(createContactRequest: CreateContactRequest): Long {
        val contact = createContactRequest.toContact()

        val message = MessageBuilder.withPayload(contact).build()
        contactChannel.output().send(message)

        contactRepository.save(contact)

        return contact.id
    }

}

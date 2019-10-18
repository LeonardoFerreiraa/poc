package br.com.leonardoferreira.poc.channel

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel

interface CreatedContactChannel {

    companion object {
        const val CREATED_CONTACT_INPUT = "created-contact-input"
        const val CREATED_CONTACT_OUTPUT = "created-contact-output"
    }

    @Input(CREATED_CONTACT_INPUT)
    fun input(): SubscribableChannel

    @Output(CREATED_CONTACT_OUTPUT)
    fun output(): MessageChannel

}

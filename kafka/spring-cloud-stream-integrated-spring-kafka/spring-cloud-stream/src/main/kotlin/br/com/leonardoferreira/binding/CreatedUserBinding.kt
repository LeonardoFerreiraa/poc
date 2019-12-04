package br.com.leonardoferreira.binding

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface CreatedUserBinding {

    companion object {
        const val CREATED_USER_INPUT = "created-user-input"
    }

    @Input(CREATED_USER_INPUT)
    fun input(): SubscribableChannel

}

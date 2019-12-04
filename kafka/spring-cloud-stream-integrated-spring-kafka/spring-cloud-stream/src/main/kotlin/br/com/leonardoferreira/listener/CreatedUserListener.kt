package br.com.leonardoferreira.listener

import br.com.leonardoferreira.CreatedUser
import br.com.leonardoferreira.binding.CreatedUserBinding
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

@Component
class CreatedUserListener {

    private val log = LoggerFactory.getLogger(CreatedUserListener::class.java)

    @StreamListener(
        value = CreatedUserBinding.CREATED_USER_INPUT,
        condition = "new String(headers['x-user-origin'])=='web'"
    )
    fun listen(message: Message<CreatedUser>) {
        log.info(
            "M=listen, I=user created, payload={}, customHeader={}",
            message.payload,
            message.headers["x-user-origin"]
        )
    }

}

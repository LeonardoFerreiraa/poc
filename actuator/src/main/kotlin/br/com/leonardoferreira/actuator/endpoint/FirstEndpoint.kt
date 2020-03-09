package br.com.leonardoferreira.actuator.endpoint

import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.http.MediaType
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
@Endpoint(id = "first-endpoint")
class FirstEndpoint {

    @ReadOperation(produces = [MediaType.TEXT_PLAIN_VALUE])
    fun greetingText(): String =
        "hello world"

    @ReadOperation(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun greetingJson(@Nullable name: String?): Map<String, String> =
        mapOf(
            "greeting" to "hello ${name ?: "world"}"
        )

}

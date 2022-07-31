package br.com.leonardoferreira.poc.domain.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CustomerAddressResponse(

    val street: String,

    val city: String,

    val number: String,

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    val createdAt: LocalDateTime,

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    val updatedAt: LocalDateTime

)
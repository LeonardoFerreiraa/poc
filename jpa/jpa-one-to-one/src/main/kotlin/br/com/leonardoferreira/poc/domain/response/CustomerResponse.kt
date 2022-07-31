package br.com.leonardoferreira.poc.domain.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CustomerResponse(

    val id: Long,

    val name: String,

    val email: String,

    val address: CustomerAddressResponse,

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    val createdAt: LocalDateTime,

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    val updatedAt: LocalDateTime

)
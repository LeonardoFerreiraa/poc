package br.com.leonardoferreira.poc.events.domain.response

data class OrderResponse(
    val id: Long,
    val price: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)

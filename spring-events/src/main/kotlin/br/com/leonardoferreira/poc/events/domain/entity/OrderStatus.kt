package br.com.leonardoferreira.poc.events.domain.entity

enum class OrderStatus {
    PENDING,
    PROCESSING,
    PROCESSED;

    fun nextStatus(): OrderStatus {
        return when (this) {
            PENDING -> PROCESSING
            PROCESSING -> PROCESSED
            else -> PROCESSED
        }
    }
}

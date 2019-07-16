package br.com.leonardoferreira.poc.events.events

import br.com.leonardoferreira.poc.events.domain.entity.Order
import br.com.leonardoferreira.poc.events.domain.entity.OrderStatus

data class OrderEvent(
    val order: Order
) {

    fun isPending(): Boolean =
        OrderStatus.PENDING == order.status

    fun isProcessing(): Boolean =
        OrderStatus.PROCESSING == order.status

    fun isProcessed(): Boolean =
        OrderStatus.PROCESSED == order.status

}

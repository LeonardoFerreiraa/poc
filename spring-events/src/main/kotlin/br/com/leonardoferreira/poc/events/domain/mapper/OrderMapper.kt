package br.com.leonardoferreira.poc.events.domain.mapper

import br.com.leonardoferreira.poc.events.domain.entity.Order
import br.com.leonardoferreira.poc.events.domain.entity.OrderStatus
import br.com.leonardoferreira.poc.events.domain.request.OrderRequest
import br.com.leonardoferreira.poc.events.domain.response.OrderResponse
import br.com.leonardoferreira.poc.events.extension.moneyFormat
import java.time.format.DateTimeFormatter

fun Order.toOrderResponse() =
    OrderResponse(
        id = this.id,
        price = this.price.moneyFormat(),
        status = this.status.name,
        createdAt = this.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
        updatedAt = this.updatedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
    )

fun OrderRequest.toOrder() =
    Order(
        price = this.price,
        status = OrderStatus.PENDING
    )

package br.com.leonardoferreira.poc.events.domain.request

import java.math.BigDecimal

data class OrderRequest(
    val price: BigDecimal
)

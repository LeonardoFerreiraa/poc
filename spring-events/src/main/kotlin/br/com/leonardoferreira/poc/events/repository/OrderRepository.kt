package br.com.leonardoferreira.poc.events.repository

import br.com.leonardoferreira.poc.events.domain.entity.Order
import br.com.leonardoferreira.poc.events.domain.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {

    fun findByStatusIn(statuses: List<OrderStatus>): List<Order>

}

package br.com.leonardoferreira.poc.events.scheduler

import br.com.leonardoferreira.poc.events.domain.entity.OrderStatus
import br.com.leonardoferreira.poc.events.repository.OrderRepository
import br.com.leonardoferreira.poc.events.service.OrderService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OrderStatusScheduler(
    private val orderRepository: OrderRepository,
    private val orderService: OrderService
) {

    @Scheduled(fixedDelayString = "PT30S")
    fun chanceStatus() {
        val orders = orderRepository.findByStatusIn(listOf(OrderStatus.PENDING, OrderStatus.PROCESSING))
        orders.forEach {
            orderService.process(it)
        }
    }

}

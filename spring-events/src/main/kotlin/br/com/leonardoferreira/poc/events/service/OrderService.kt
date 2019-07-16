package br.com.leonardoferreira.poc.events.service

import br.com.leonardoferreira.poc.events.domain.entity.Order
import br.com.leonardoferreira.poc.events.domain.mapper.toOrder
import br.com.leonardoferreira.poc.events.domain.mapper.toOrderResponse
import br.com.leonardoferreira.poc.events.domain.request.OrderRequest
import br.com.leonardoferreira.poc.events.domain.response.OrderResponse
import br.com.leonardoferreira.poc.events.events.OrderEvent
import br.com.leonardoferreira.poc.events.exception.ResourceNotFoundException
import br.com.leonardoferreira.poc.events.repository.OrderRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

interface OrderService {
    fun findById(id: Long): OrderResponse
    fun create(orderRequest: OrderRequest): Long
    fun process(order: Order)
}

@Service
internal class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : OrderService {
    @Transactional(readOnly = true)
    override fun findById(id: Long): OrderResponse {
        val order = orderRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException()
        return order.toOrderResponse()
    }

    @Transactional
    override fun create(orderRequest: OrderRequest): Long {
        val order = orderRepository.save(orderRequest.toOrder())
        applicationEventPublisher.publishEvent(OrderEvent(order))

        return order.id
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun process(order: Order) {
        order.nextStatus()
        orderRepository.save(order)
    }

}

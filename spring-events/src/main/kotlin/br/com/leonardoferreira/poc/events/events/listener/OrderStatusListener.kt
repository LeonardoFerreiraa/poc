package br.com.leonardoferreira.poc.events.events.listener

import br.com.leonardoferreira.poc.events.events.OrderEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class OrderStatusListener {
    private val log = LoggerFactory.getLogger(OrderStatusListener::class.java)

    @EventListener(condition = "#event.pending")
    fun handlePending(event: OrderEvent) {
        log.info("event=pending, order={}", event.order)
    }

    @EventListener(condition = "#event.processing")
    fun handleProcessing(event: OrderEvent) {
        log.info("event=processing, order={}", event.order)
    }

    @EventListener(condition = "#event.processed")
    fun handleProcessed(event: OrderEvent) {
        log.info("event=processed, order={}", event.order)
    }

}

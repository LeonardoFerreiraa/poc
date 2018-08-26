package br.com.leonardoferreira.poc.statemachine.service;

import br.com.leonardoferreira.poc.statemachine.statemachine.OrderStateMachineInterceptor;
import br.com.leonardoferreira.poc.statemachine.domain.Order;
import br.com.leonardoferreira.poc.statemachine.domain.enumeration.OrderEvents;
import br.com.leonardoferreira.poc.statemachine.domain.enumeration.OrderStates;
import br.com.leonardoferreira.poc.statemachine.exception.ResourceNotFound;
import br.com.leonardoferreira.poc.statemachine.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StateMachineFactory<OrderStates, OrderEvents> factory;

    @Autowired
    private OrderStateMachineInterceptor orderStateMachineInterceptor;

    @Transactional
    public Long create(BigDecimal price, String sku) {
        Order order = new Order();

        order.setOrderCode(UUID.randomUUID().toString());
        order.setPrice(price);
        order.setSku(sku);
        order.setState(OrderStates.SUBMITTED);

        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public StateMachine<OrderStates, OrderEvents> fulfill(Long orderId) {
        StateMachine<OrderStates, OrderEvents> stateMachine = build(orderId);

        Message<OrderEvents> messageEvent = MessageBuilder.withPayload(OrderEvents.FUFILL)
                .setHeader("orderId", orderId)
                .build();
        stateMachine.sendEvent(messageEvent);

        return stateMachine;
    }

    @Transactional
    public StateMachine<OrderStates, OrderEvents> pay(Long orderId) {
        StateMachine<OrderStates, OrderEvents> stateMachine = build(orderId);

        Message<OrderEvents> messageEvent = MessageBuilder.withPayload(OrderEvents.PAY)
                .setHeader("orderId", orderId)
                .build();
        stateMachine.sendEvent(messageEvent);

        return stateMachine;
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(ResourceNotFound::new);
    }

    @Transactional
    public void updateStatus(Long id, OrderStates state) {
        Order order = findById(id);
        order.setState(state);
        orderRepository.save(order);
    }

    @Transactional
    public StateMachine<OrderStates, OrderEvents> cancel(Long orderId) {
        StateMachine<OrderStates, OrderEvents> stateMachine = build(orderId);

        Message<OrderEvents> cancelMessage = MessageBuilder.withPayload(OrderEvents.CANCEL)
                .setHeader("orderId", orderId)
                .build();

        stateMachine.sendEvent(cancelMessage);

        return stateMachine;
    }

    private StateMachine<OrderStates, OrderEvents> build(Long orderId) {
        Order order = findById(orderId);

        StateMachine<OrderStates, OrderEvents> stateMachine = factory.getStateMachine(order.getOrderCode());

        stateMachine.stop();
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(orderStateMachineInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(order.getState(), null, null, null));
                });
        stateMachine.start();

        return stateMachine;
    }

}

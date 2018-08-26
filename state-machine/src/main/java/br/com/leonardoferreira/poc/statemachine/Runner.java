package br.com.leonardoferreira.poc.statemachine;

import br.com.leonardoferreira.poc.statemachine.domain.enumeration.OrderEvents;
import br.com.leonardoferreira.poc.statemachine.domain.enumeration.OrderStates;
import br.com.leonardoferreira.poc.statemachine.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class Runner implements ApplicationRunner {

    @Autowired
    private OrderService orderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("----------------------------------------------------");
        Long orderId = orderService.create(new BigDecimal("32.43"), "BR-LEFE-001");

        StateMachine<OrderStates, OrderEvents> payStateMachine = orderService.pay(orderId);
        log.info(">> pay state: {}", payStateMachine.getState().getId());

        StateMachine<OrderStates, OrderEvents> fulfillStateMachine = orderService.fulfill(orderId);
        log.info(">> fulfill state: {}", fulfillStateMachine.getState().getId());

        StateMachine<OrderStates, OrderEvents> cancelStateMachine = orderService.cancel(orderId);
        log.info(">> cancel state: {}", cancelStateMachine.getState().getId());

        log.info("----------------------------------------------------");
    }

}

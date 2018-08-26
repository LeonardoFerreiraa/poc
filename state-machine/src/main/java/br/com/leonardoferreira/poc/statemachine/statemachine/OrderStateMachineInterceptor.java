package br.com.leonardoferreira.poc.statemachine.statemachine;

import br.com.leonardoferreira.poc.statemachine.domain.enumeration.OrderEvents;
import br.com.leonardoferreira.poc.statemachine.domain.enumeration.OrderStates;
import br.com.leonardoferreira.poc.statemachine.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderStateMachineInterceptor extends StateMachineInterceptorAdapter<OrderStates, OrderEvents> {

    @Autowired
    private OrderService orderService;

    @Override
    public void preStateChange(State<OrderStates, OrderEvents> state, Message<OrderEvents> message,
                               Transition<OrderStates, OrderEvents> transition, StateMachine<OrderStates, OrderEvents> stateMachine) {
        if (message != null) {
            Long orderId = (Long) message.getHeaders().get("orderId");
            log.info("> updating order {}", orderId);

            if (orderId != null) {
                orderService.updateStatus(orderId, state.getId());
            }
        }
    }
}

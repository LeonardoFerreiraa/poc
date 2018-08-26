package br.com.leonardoferreira.poc.statemachine.statemachine;

import br.com.leonardoferreira.poc.statemachine.domain.enumeration.OrderEvents;
import br.com.leonardoferreira.poc.statemachine.domain.enumeration.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
public class OrderStateMachineListener extends StateMachineListenerAdapter<OrderStates, OrderEvents> {

    @Override
    public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
        OrderStates stateFrom = from == null ? null : from.getId();
        OrderStates stateTo = to == null ? null : to.getId();

        log.info("> State Changed from={}, to={}", stateFrom, stateTo);
    }

}

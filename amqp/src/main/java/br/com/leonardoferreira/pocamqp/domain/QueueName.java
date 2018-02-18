package br.com.leonardoferreira.pocamqp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lferreira on 2/17/18
 */
@Getter
@AllArgsConstructor
public enum QueueName {
    QUEUE_A("queue-a"),
    QUEUE_B("queue-b"),
    QUEUE_C("queue-c"),
    QUEUE_D("queue-d"),
    ;

    private String name;

    public String getExchange() {
        return name + "-exchange";
    }

}

package br.com.leonardoferreira.pocamqp.controller;

import br.com.leonardoferreira.pocamqp.domain.QueueName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lferreira on 2/15/18
 */
@Slf4j
@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private List<SimpleMessageListenerContainer> queues;

    @GetMapping("/push/{queue}/{msg}")
    public String push(@PathVariable QueueName queue, @PathVariable String msg) {
        log.info("M=push, MSG=sending message");
        rabbitTemplate.convertAndSend(queue.getName(), msg);
        return msg;
    }

    @GetMapping("/check-queues")
    public Map<List<String>, Map<String, Object>> checkConsumers() {
        Map<List<String>, Map<String, Object>> result = new HashMap<>();
        for (SimpleMessageListenerContainer queue : queues) {
            result.put(Arrays.asList(queue.getQueueNames()), new HashMap<String, Object>(){{
                put("isActive", queue.isActive());
                put("isRunning", queue.isRunning());
                put("activeConsumerCount", queue.getActiveConsumerCount());
            }});
        }
        return result;
    }

    @GetMapping("/stop/{queue}")
    public String stopQueue(@PathVariable QueueName queue) {
        for (SimpleMessageListenerContainer q : queues) {
            List<String> qs = Arrays.asList(q.getQueueNames());
            if (qs.contains(queue.getName())) {
                q.shutdown();
                return String.format("SHUTDOWN QUEUE %s", qs);
            }
        }
        return "NOT FOUND";
    }

    @GetMapping("/start/{queue}")
    public String startQueue(@PathVariable QueueName queue) {
        for (SimpleMessageListenerContainer q : queues) {
            List<String> qs = Arrays.asList(q.getQueueNames());
            if (qs.contains(queue.getName())) {
                q.start();
                return String.format("START QUEUE %s", qs);
            }
        }
        return "NOT FOUND";
    }

    @GetMapping("/restart/{queue}")
    public String restartQueue(@PathVariable QueueName queue) {
        for (SimpleMessageListenerContainer q : queues) {
            List<String> qs = Arrays.asList(q.getQueueNames());
            if (qs.contains(queue.getName())) {
                return String.format("START QUEUE %s", qs);
            }
        }
        return "NOT FOUND";
    }

}

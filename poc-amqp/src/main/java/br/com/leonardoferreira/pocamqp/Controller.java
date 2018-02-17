package br.com.leonardoferreira.pocamqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lferreira on 2/15/18
 */
@Slf4j
@RestController
//@Profile("producer")
public class Controller {

    @Autowired
    private RabbitMessagingTemplate rabbitTemplate;

    @GetMapping("/push/{msg}")
    public String push(@PathVariable String msg) {
        log.info("M=push, MSG=sending message");
        rabbitTemplate.convertAndSend("TestQ", msg);
        return msg;
    }

}

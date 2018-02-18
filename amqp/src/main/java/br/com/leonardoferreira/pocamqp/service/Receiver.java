package br.com.leonardoferreira.pocamqp.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by lferreira on 2/15/18
 */
@Slf4j
@Service
public class Receiver {

    public void receiveMessage(String message) {
        log.info("M=receiveMessage, message={}", message);
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

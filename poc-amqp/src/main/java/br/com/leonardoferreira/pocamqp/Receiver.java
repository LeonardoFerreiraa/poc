package br.com.leonardoferreira.pocamqp;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lferreira on 2/15/18
 */
@Slf4j
@Service
public class ReceiverService {

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        log.info("M=receiveMessage, message={}", message);
        latch.countDown();
    }

}

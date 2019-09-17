package br.com.leonardoferreira.consumer.listener;

import br.com.leonardoferreira.CreatedUser;
import br.com.leonardoferreira.consumer.channel.CreatedUserChannel;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreatedUserListener {

    public final static AtomicInteger counter = new AtomicInteger(0);

    @StreamListener(CreatedUserChannel.CREATED_USER_INPUT)
    public void listen(final CreatedUser createdUser) {
        log.info("M=listen, createdUser={}", createdUser);
        counter.incrementAndGet();
    }

}

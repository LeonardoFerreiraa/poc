package br.com.leonardoferreira.consumer.listener;

import br.com.leonardoferreira.CreatedUser;
import br.com.leonardoferreira.consumer.channel.CreatedUserChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreatedUserListener {

    @StreamListener(CreatedUserChannel.CREATED_USER_INPUT)
    public void listener(final CreatedUser createdUser) {
        log.info("M=listener, createdUser={}", createdUser);
    }

}

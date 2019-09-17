package br.com.leonardoferreira.consumer.integration;

import br.com.leonardoferreira.CreatedUser;
import br.com.leonardoferreira.consumer.channel.CreatedUserChannel;
import br.com.leonardoferreira.consumer.listener.CreatedUserListener;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateUserListenerIntegrationTest {

    private final CreatedUserChannel createdUserChannel;

    @Autowired
    CreateUserListenerIntegrationTest(final CreatedUserChannel createdUserChannel) {
        this.createdUserChannel = createdUserChannel;
    }

    @Test
    void listenCreatedUserTest() {
        CreatedUser createdUser = CreatedUser.newBuilder()
                .setId(System.currentTimeMillis())
                .setName("Leonardo")
                .setEmail("mail2@leonardoferreira.com.br")
                .setCreatedAt(LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant())
                .setUpdatedAt(LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant())
                .build();

        final Message<CreatedUser> message = MessageBuilder.withPayload(createdUser).build();

        createdUserChannel.input()
                .send(message);

        Assertions.assertEquals(1, CreatedUserListener.counter.get());
    }
}

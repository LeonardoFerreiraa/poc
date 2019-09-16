package br.com.leonardoferreira.consumer.integration;

import br.com.leonardoferreira.CreatedUser;
import br.com.leonardoferreira.consumer.channel.CreatedUserChannel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreatedUserIntegrationTest {

    private final CreatedUserChannel createdUserChannel;

    @Autowired
    CreatedUserIntegrationTest(final CreatedUserChannel createdUserChannel) {
        this.createdUserChannel = createdUserChannel;
    }

    @Test
    void listenerTest(final CapturedOutput capturedOutput) {
        final CreatedUser createdUser = CreatedUser.newBuilder()
                .setId(System.currentTimeMillis())
                .setName("Leonardo")
                .setEmail("mail@leonardoferreira.com.br")
                .setCreatedAt(LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant())
                .setUpdatedAt(LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant())
                .build();

        final Message<CreatedUser> message = MessageBuilder
                .withPayload(createdUser)
                .build();

        createdUserChannel.input()
                .send(message);

        Assertions.assertThat(capturedOutput)
                .contains("M=listener, createdUser=" + createdUser.toString());
    }
}

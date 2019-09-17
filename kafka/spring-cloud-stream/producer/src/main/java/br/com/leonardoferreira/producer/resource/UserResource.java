package br.com.leonardoferreira.producer.resource;

import br.com.leonardoferreira.CreatedUser;
import br.com.leonardoferreira.producer.channel.CreatedUserChannel;
import br.com.leonardoferreira.producer.request.CreateUserRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserResource {

    private final CreatedUserChannel createdUserChannel;

    public UserResource(final CreatedUserChannel createdUserChannel) {
        this.createdUserChannel = createdUserChannel;
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody final CreateUserRequest request) {
        final CreatedUser createdUser = CreatedUser.newBuilder()
                .setId(System.currentTimeMillis())
                .setName(request.getName())
                .setEmail(request.getEmail())
                .setCreatedAt(LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant())
                .setUpdatedAt(LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant())
                .build();

        log.info("M=create, createdUser={}", createdUser);
        final Message<CreatedUser> message = MessageBuilder.withPayload(createdUser).build();

        createdUserChannel.output().send(message);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

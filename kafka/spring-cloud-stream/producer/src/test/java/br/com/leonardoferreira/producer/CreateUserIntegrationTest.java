package br.com.leonardoferreira.producer;

import br.com.leonardoferreira.CreatedUser;
import br.com.leonardoferreira.producer.channel.CreatedUserChannel;
import br.com.leonardoferreira.producer.request.CreateUserRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CreateUserIntegrationTest {

    private final MessageCollector messageCollector;

    private final CreatedUserChannel createdUserChannel;

    @Autowired
    CreateUserIntegrationTest(final MessageCollector messageCollector,
                              final CreatedUserChannel createdUserChannel) {
        this.messageCollector = messageCollector;
        this.createdUserChannel = createdUserChannel;
    }

    @Test
    void produceMessageTest() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setName("Leonardo");
        request.setEmail("mail@leonardoferreira.com.br");

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(request)
                .when()
                    .post("/users")
                .then()
                    .log().all()
                    .statusCode(HttpServletResponse.SC_CREATED);
        // @formatter:on

        final CreatedUser createdUser = pollMessage();
        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals(request.getName(), createdUser.getName());
        Assertions.assertEquals(request.getEmail(), createdUser.getEmail());
        Assertions.assertNotNull(createdUser.getCreatedAt());
        Assertions.assertNotNull(createdUser.getUpdatedAt());
    }

    @SuppressWarnings("unchecked")
    private CreatedUser pollMessage() {
        final Message<CreatedUser> message = (Message<CreatedUser>) messageCollector.forChannel(createdUserChannel.output()).poll();
        return message == null ? null : message.getPayload();
    }
}

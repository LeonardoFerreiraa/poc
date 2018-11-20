package br.com.leonardoferreira.poc.hystrix.client;

import br.com.leonardoferreira.poc.hystrix.client.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Exemplo de integração com o feign")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserClientTest {

    @Autowired
    private UserClient userClient;

    @Test
    @DisplayName("Retorna fallback")
    void returnsFallback() {
        User user = userClient.findByName("leonardoferreiraaa");
        Assertions.assertNull(user.getId());
        Assertions.assertEquals("leonardoferreiraaa", user.getLogin());
    }
}

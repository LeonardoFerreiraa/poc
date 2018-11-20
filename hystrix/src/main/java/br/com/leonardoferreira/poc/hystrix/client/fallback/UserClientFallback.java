package br.com.leonardoferreira.poc.hystrix.client.fallback;

import br.com.leonardoferreira.poc.hystrix.client.UserClient;
import br.com.leonardoferreira.poc.hystrix.client.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public User findByName(String name) {
        User user = new User();
        user.setLogin(name);

        return user;
    }
}

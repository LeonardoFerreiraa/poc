package br.com.leonardoferreira.poc.hystrix.client;

import br.com.leonardoferreira.poc.hystrix.client.domain.User;
import br.com.leonardoferreira.poc.hystrix.client.fallback.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-client", url = "https://api.github.com/users", fallback = UserClientFallback.class)
public interface UserClient {

    @GetMapping("/{name}")
    User findByName(@PathVariable("name") String name);

}

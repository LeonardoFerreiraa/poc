package br.com.leonardoferreira.poc.hystrix.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "test-client-1", url = "https://api.github.com", fallback = GithubClient.ClientFallback.class)
public interface GithubClient {

    @GetMapping("/users/{name}")
    String searchRepos(@PathVariable String name);

    @Component
    class ClientFallback implements GithubClient {

        @Override
        public String searchRepos(String name) {
            return ":(";
        }
    }
}

package br.com.leonardoferreira.poc.feign.client;

import br.com.leonardoferreira.poc.feign.domain.Todo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "todoClient",
        url = "${jsonplaceholder.url}/todos"
)
public interface TodoClient {

    @GetMapping("/{id}")
    Todo findById(@PathVariable("id") Long id);

    @GetMapping
    List<Todo> findByUserId(@RequestParam("userId") Long userId);

}

package br.com.leonardoferreira.poc.hystrix.controller;

import br.com.leonardoferreira.poc.hystrix.client.TestClient1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author s2it_leferreira
 * @since 25/06/18 17:11
 */
@RestController
@RequestMapping("/test/client")
public class TestClientController {

    @Autowired
    private TestClient1 testClient1;

    @GetMapping("/1")
    public String test1(@RequestParam final String name) {
        return testClient1.searchRepos(name);
    }

}

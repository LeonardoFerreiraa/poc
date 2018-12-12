package br.com.leonardoferreira.poc.apiversion.controller.v1.url;

import br.com.leonardoferreira.poc.apiversion.domain.response.v1.Contact;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("urlContactV1")
@RequestMapping("/v1/contacts")
public class ContactController {

    @GetMapping
    public List<Contact> index() {
        return Arrays.asList(new Contact(1L, "Foo"),
                new Contact(2L, "Bar"));
    }

}

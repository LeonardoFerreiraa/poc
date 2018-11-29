package br.com.leonardoferreira.poc.apiversion.controller.v1.header;

import br.com.leonardoferreira.poc.apiversion.domain.Contact;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("headerContactV1")
@RequestMapping("/contacts")
public class ContactController {

    @GetMapping(headers = { "Api-Version: 1" })
    public List<Contact> index() {
        return Arrays.asList(new Contact(1L, "Foo"),
                new Contact(2L, "Bar"));
    }

}

package br.com.leonardoferreira.poc.apiversion.controller.v2.header;

import br.com.leonardoferreira.poc.apiversion.domain.response.v2.Contact;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/contacts")
@RestController("headerContactV2")
public class ContactController {

    @GetMapping(produces = "application/vnd.poc.v2+json")
    public List<Contact> contentNegotiation2() {
        return Arrays.asList(new Contact(1L, "Foo Silva", "foo@silva.com"),
                new Contact(2L, "Bar Souza", "bar@souza.com"));
    }

}

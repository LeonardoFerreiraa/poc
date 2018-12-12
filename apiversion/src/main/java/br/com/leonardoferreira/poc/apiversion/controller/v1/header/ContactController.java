package br.com.leonardoferreira.poc.apiversion.controller.v1.header;

import br.com.leonardoferreira.poc.apiversion.domain.response.v1.Contact;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/contacts")
@RestController("headerContactV1")
public class ContactController {

    @GetMapping(produces = "application/vnd.poc.v1+json" )
    public List<Contact> contentNegotiation() {
        return Arrays.asList(new Contact(1L, "Foo"),
                new Contact(2L, "Bar"));
    }

}

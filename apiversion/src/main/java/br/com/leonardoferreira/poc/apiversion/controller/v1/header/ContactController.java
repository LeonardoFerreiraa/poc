package br.com.leonardoferreira.poc.apiversion.controller.v1.header;

import br.com.leonardoferreira.poc.apiversion.domain.response.v1.Contact;
import io.swagger.annotations.Api;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "headerContactV1", tags = "headerContactV1")
@RequestMapping("/contacts")
@RestController("headerContactV1")
public class ContactController {

    @GetMapping(headers = { "Api-Version=1" })
    public List<Contact> customHeader1() {
        return Arrays.asList(new Contact(1L, "Foo"),
                new Contact(2L, "Bar"));
    }

    @GetMapping(headers = { "Accept=application/vnd.poc.v1+json" })
    public List<Contact> contentNegotiation1() {
        return Arrays.asList(new Contact(1L, "Foo"),
                new Contact(2L, "Bar"));
    }

}

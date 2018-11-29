package br.com.leonardoferreira.poc.apiversion.controller.v2.url;

import br.com.leonardoferreira.poc.apiversion.domain.response.v2.Contact;
import io.swagger.annotations.Api;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "urlContactV2", tags = "urlContactV2")
@RestController("urlContactV2")
@RequestMapping("/v2/contacts")
public class ContactController {

    @GetMapping
    public List<Contact> index() {
        return Arrays.asList(new Contact(1L, "Foo Silva", "foo@silva.com"),
                new Contact(2L, "Bar Souza", "bar@souza.com"));
    }

}

package br.com.leonardoferreira.auditing.controller.history;

import br.com.leonardoferreira.auditing.domain.audit.response.ContactHistoryResponse;
import br.com.leonardoferreira.auditing.service.history.ContactHistoryService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts/{id}/histories")
public class ContactHistoryController {

    private final ContactHistoryService contactHistoryService;

    public ContactHistoryController(final ContactHistoryService contactHistoryService) {
        this.contactHistoryService = contactHistoryService;
    }

    @GetMapping
    public List<ContactHistoryResponse> findByContactId(@PathVariable final Long id) {
        return contactHistoryService.findByContactId(id);
    }

}

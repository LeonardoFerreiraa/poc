package br.com.leonardoferreira.auditing.service.history;

import br.com.leonardoferreira.auditing.domain.audit.ContactHistory;
import br.com.leonardoferreira.auditing.domain.audit.response.ContactHistoryResponse;
import br.com.leonardoferreira.auditing.repository.history.ContactHistoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactHistoryService {

    private final ContactHistoryRepository contactRepository;

    public ContactHistoryService(final ContactHistoryRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public List<ContactHistoryResponse> findByContactId(final Long contactId) {
        return contactRepository.findByContactId(contactId).stream()
                .map(ContactHistory::toContactHistoryResponse)
                .collect(Collectors.toList());
    }

}

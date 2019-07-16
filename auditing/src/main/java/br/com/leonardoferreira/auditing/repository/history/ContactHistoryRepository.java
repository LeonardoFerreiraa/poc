package br.com.leonardoferreira.auditing.repository.history;

import br.com.leonardoferreira.auditing.domain.audit.ContactHistory;
import br.com.leonardoferreira.auditing.domain.audit.id.HistoryId;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.Repository;

public interface ContactHistoryRepository extends Repository<ContactHistory, HistoryId> {

    @EntityGraph(attributePaths = {"revision"})
    List<ContactHistory> findByContactId(Long contactId);

}

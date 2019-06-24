package br.com.leonardoferreira.auditing.repository.history;

import br.com.leonardoferreira.auditing.domain.audit.id.HistoryId;
import br.com.leonardoferreira.auditing.domain.audit.ContactHistory;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface ContactHistoryRepository extends Repository<ContactHistory, HistoryId> {

    List<ContactHistory> findAll();

}

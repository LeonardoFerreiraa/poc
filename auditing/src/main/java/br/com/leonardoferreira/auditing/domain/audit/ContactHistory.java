package br.com.leonardoferreira.auditing.domain.audit;

import br.com.leonardoferreira.auditing.domain.entity.Contact;
import br.com.leonardoferreira.auditing.domain.audit.id.HistoryId;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ContactHistory {

    @EmbeddedId
    private HistoryId id;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Contact contact;

}

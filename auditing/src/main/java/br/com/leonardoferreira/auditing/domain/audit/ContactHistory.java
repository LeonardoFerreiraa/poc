package br.com.leonardoferreira.auditing.domain.audit;

import br.com.leonardoferreira.auditing.domain.audit.id.HistoryId;
import br.com.leonardoferreira.auditing.domain.audit.response.ContactHistoryResponse;
import br.com.leonardoferreira.auditing.domain.response.ContactResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.envers.RevisionType;

@Data
@Entity
public class ContactHistory {

    @EmbeddedId
    private HistoryId historyId;

    @ManyToOne
    @JoinColumn(name = "revision", insertable = false, updatable = false)
    private Revision revision;

    @Column(name = "id", insertable = false, updatable = false)
    private Long contactId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated
    @Column(name = "revision_type", nullable = false)
    private RevisionType revisionType;

    public ContactHistoryResponse toContactHistoryResponse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return ContactHistoryResponse.builder()
                .type(revisionType.name())
                .auditDate(revision.getAuditDate().format(formatter))
                .revision(revision.getUuid())
                .contact(this.toContactResponse())
                .build();
    }

    private ContactResponse toContactResponse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return ContactResponse.builder()
                .id(contactId)
                .name(name)
                .email(email)
                .createdAt(createdAt.format(formatter))
                .updatedAt(updatedAt.format(formatter))
                .build();
    }

}

package br.com.leonardoferreira.auditing.domain.audit;

import br.com.leonardoferreira.auditing.domain.audit.listener.HistoryListener;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Data
@Entity
@RevisionEntity(HistoryListener.class)
public class Revision {

    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @RevisionTimestamp
    @Column(nullable = false)
    private Long timestamp;

    @Column(nullable = false)
    private String uuid;

    public LocalDateTime getAuditDate() {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                TimeZone.getDefault().toZoneId()
        );
    }

}

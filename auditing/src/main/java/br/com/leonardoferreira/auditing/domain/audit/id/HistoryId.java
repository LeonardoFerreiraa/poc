package br.com.leonardoferreira.auditing.domain.audit.id;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class HistoryId implements Serializable {

    private Long id;

    private Long revision;

}

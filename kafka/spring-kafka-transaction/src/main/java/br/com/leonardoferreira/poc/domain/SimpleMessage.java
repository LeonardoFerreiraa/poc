package br.com.leonardoferreira.poc.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
public class SimpleMessage {

    @Id
    private String id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public SimpleMessage() {
    }

    public SimpleMessage(final String id) {
        this.id = id;
    }

}

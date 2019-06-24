package br.com.leonardoferreira.auditing.domain.entity;


import br.com.leonardoferreira.auditing.domain.request.ContactRequest;
import br.com.leonardoferreira.auditing.domain.response.ContactResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

@Data
@Entity
@Audited
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public ContactResponse toContactResponse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return ContactResponse.builder()
                .id(id)
                .name(name)
                .email(email)
                .createdAt(createdAt.format(formatter))
                .updatedAt(updatedAt.format(formatter))
                .build();
    }

    public void updateFromRequest(final ContactRequest contactRequest) {
        this.name = contactRequest.getName();
        this.email = contactRequest.getEmail();
    }
}

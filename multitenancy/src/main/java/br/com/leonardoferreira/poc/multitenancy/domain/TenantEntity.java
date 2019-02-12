package br.com.leonardoferreira.poc.multitenancy.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@FilterDef(name = "tenantFilter",
        defaultCondition = "owner = :currentUser",
        parameters = @ParamDef(name = "currentUser", type = "string"))
@Filter(name = "tenantFilter")
@EntityListeners(AuditingEntityListener.class)
public class TenantEntity {

    @CreationTimestamp
    @Column(unique = true)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(unique = true)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(unique = true)
    private String owner;

}

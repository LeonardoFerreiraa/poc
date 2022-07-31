package br.com.leonardoferreira.poc.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.PrimaryKeyJoinColumn

@Entity
data class CustomerAddress(

    @Id
    val id: Long = 0,

    @Column(nullable = false)
    val street: String,

    @Column(nullable = false)
    val city: String,

    @Column(nullable = false)
    val number: String,

    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()

) {

    @OneToOne
    @PrimaryKeyJoinColumn
    lateinit var customer: Customer

}
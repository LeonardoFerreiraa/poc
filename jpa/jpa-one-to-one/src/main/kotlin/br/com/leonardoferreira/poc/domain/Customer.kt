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
data class Customer(

    @Id
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()

) {

    @OneToOne
    @PrimaryKeyJoinColumn
    lateinit var customerAddress: CustomerAddress

    companion object

}
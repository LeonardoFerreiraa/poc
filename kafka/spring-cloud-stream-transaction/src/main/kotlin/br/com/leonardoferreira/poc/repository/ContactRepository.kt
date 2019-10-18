package br.com.leonardoferreira.poc.repository

import br.com.leonardoferreira.poc.domain.Contact
import org.springframework.data.jpa.repository.JpaRepository

interface ContactRepository : JpaRepository<Contact, Long>

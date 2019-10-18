package br.com.leonardoferreira.poc.mapper

import br.com.leonardoferreira.poc.domain.Contact
import br.com.leonardoferreira.poc.domain.request.CreateContactRequest

fun CreateContactRequest.toContact() =
    Contact(
        name = this.name,
        email = this.email
    )

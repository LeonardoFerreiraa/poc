package br.com.leonardoferreira.poc.tornadofx.model

import tornadofx.*
import java.time.LocalDate

data class Contact(
    val name: String,
    val email: String,
    val phone: String,
    val birthDay: LocalDate
)

class ContactModel : ItemViewModel<Contact>() {
    val name = bind(Contact::name)
    val email = bind(Contact::email)
    val phone = bind(Contact::phone)
    val birthDay = bind(Contact::birthDay)

    override fun onCommit() {
        item = Contact(
            name = name.value,
            email = email.value,
            phone = phone.value,
            birthDay = birthDay.value
        )
    }
}

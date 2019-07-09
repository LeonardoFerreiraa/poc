package br.com.leonardoferreira.poc.tornadofx.model

import tornadofx.*
import java.time.LocalDate

class Contact(
    name: String? = null,
    email: String? = null,
    phone: String? = null,
    birthDay: LocalDate? = null
) {

    var name by property(name)
    fun nameProperty() = getProperty(Contact::name)

    var email by property(email)
    fun emailProperty() = getProperty(Contact::email)

    var phone by property(phone)
    fun phoneProperty() = getProperty(Contact::phone)

    var birthDay by property(birthDay)
    fun birthDayProperty() = getProperty(Contact::birthDay)

    override fun toString(): String =
        "Contact(name=$name, email=$email, phone=$phone, birthDay=$birthDay)"

}

class ContactModel : ItemViewModel<Contact>(Contact()) {
    val name = bind(Contact::nameProperty)
    val email = bind(Contact::emailProperty)
    val phone = bind(Contact::phoneProperty)
    val birthDay = bind(Contact::birthDayProperty)
}

package br.com.leonardoferreira.poc.tornadofx.controller

import br.com.leonardoferreira.poc.tornadofx.events.ContactCreatedEvent
import br.com.leonardoferreira.poc.tornadofx.model.Contact
import javafx.collections.ObservableList
import tornadofx.*

class ContactController : Controller() {

    private val contacts: MutableList<Contact> = mutableListOf()

    fun findAll(): ObservableList<Contact> {
        return contacts.toObservable()
    }

    fun save(contact: Contact) {
        contacts.add(contact)
        fire(ContactCreatedEvent())
    }

}

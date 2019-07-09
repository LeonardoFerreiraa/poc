package br.com.leonardoferreira.poc.tornadofx.controller

import br.com.leonardoferreira.poc.tornadofx.model.Contact
import javafx.collections.ObservableList
import tornadofx.*
import java.time.LocalDate

class ContactController : Controller() {

    fun findAll(): ObservableList<Contact> {
        return listOf(
            Contact(
                name = "name1",
                email = "name1@email.com",
                phone = "11111",
                birthDay = LocalDate.of(1990, 1, 1)
            ),
            Contact(
                name = "name2",
                email = "name2@email.com",
                phone = "11112",
                birthDay = LocalDate.of(1990, 1, 2)
            ),
            Contact(
                name = "name3",
                email = "name3@email.com",
                phone = "11113",
                birthDay = LocalDate.of(1990, 1, 3)
            )
        ).toObservable()
    }

}

package br.com.leonardoferreira.poc.tornadofx.view

import br.com.leonardoferreira.poc.tornadofx.controller.ContactController
import br.com.leonardoferreira.poc.tornadofx.events.ContactCreatedEvent
import br.com.leonardoferreira.poc.tornadofx.model.Contact
import tornadofx.*
import java.time.format.DateTimeFormatter

class ContactTableView : View() {
    private val contactController by inject<ContactController>()

    override val root = tableview(contactController.findAll()) {
        columnResizePolicy = SmartResize.POLICY

        readonlyColumn("Nome", Contact::name) {
            pctWidth(25.0)
        }
        readonlyColumn("Email", Contact::email) {
            pctWidth(25.0)
        }
        readonlyColumn("Telefone", Contact::phone) {
            pctWidth(25.0)
        }
        readonlyColumn("Aniversário", Contact::birthDay) {
            pctWidth(25.0)
            cellFormat {
                text = it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }
        }

        subscribe<ContactCreatedEvent> {
            items.setAll(contactController.findAll())
        }
    }
}

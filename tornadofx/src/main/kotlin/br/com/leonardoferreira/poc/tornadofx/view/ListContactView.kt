package br.com.leonardoferreira.poc.tornadofx.view

import br.com.leonardoferreira.poc.tornadofx.controller.ContactController
import br.com.leonardoferreira.poc.tornadofx.model.Contact
import tornadofx.*
import java.time.format.DateTimeFormatter

class ListContactView : View("Listagem de Contatos") {
    private val controller: ContactController by inject()

    override val root = vbox(spacing = 10) {
        style {
            padding = box(20.px)
        }

        tableview(controller.findAll()) {
            columnResizePolicy = SmartResize.POLICY

            column("Nome", Contact::name)
            column("Email", Contact::email)
            column("Telefone", Contact::phone)
            column("Aniversário", String::class) {
                value {
                    it.value.birthDay.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                }
            }
        }

        button("Voltar") {
            action {
                find<MainView>().openWindow(owner = null)
                currentStage?.close()
            }
        }

    }
}

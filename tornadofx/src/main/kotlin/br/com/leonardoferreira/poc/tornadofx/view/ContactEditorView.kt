package br.com.leonardoferreira.poc.tornadofx.view

import tornadofx.*

class ContactEditorView : View("Agenda") {
    override val root = borderpane {
        style {
            padding = box(20.px)
        }

        left<ContactFormView>()
        center<ContactTableView>()

        bottom {
            button("Voltar") {
                action {
                    find<MainView>().openWindow(owner = null)
                    currentStage?.close()
                }
            }
        }

    }
}


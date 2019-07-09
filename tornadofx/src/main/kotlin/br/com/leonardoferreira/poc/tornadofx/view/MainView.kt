package br.com.leonardoferreira.poc.tornadofx.view

import tornadofx.*

class MainView : View("Agenda") {

    override val root = vbox(spacing = 10) {
        style {
            padding = box(20.px)
            minWidth = 100.px
        }

        button("Ver Contatos") {
            useMaxWidth = true

            action {
                find<ListContactView>().openWindow(owner = null)
                currentStage?.close()
            }
        }
    }

}

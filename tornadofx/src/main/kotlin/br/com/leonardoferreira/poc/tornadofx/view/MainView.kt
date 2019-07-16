package br.com.leonardoferreira.poc.tornadofx.view

import tornadofx.*

class MainView : View("Agenda menu") {
    override val root = vbox(spacing = 10) {
        if (config.boolean("alreadyAccessed") != true) {
            log.info("first access")
            config.set("alreadyAccessed" to true)
            config.save()
        }

        style {
            padding = box(20.px)
            minWidth = 100.px
        }

        button("Gerenciar Contatos") {
            useMaxWidth = true

            action {
                find<ContactEditorView>().openWindow(owner = null)
                currentStage?.close()
            }
        }
    }

}

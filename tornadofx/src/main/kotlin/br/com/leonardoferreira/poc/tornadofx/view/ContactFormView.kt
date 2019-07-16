package br.com.leonardoferreira.poc.tornadofx.view

import br.com.leonardoferreira.poc.tornadofx.controller.ContactController
import br.com.leonardoferreira.poc.tornadofx.extension.isValidEmail
import br.com.leonardoferreira.poc.tornadofx.model.ContactModel
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class ContactFormView : View() {
    private val contactController by inject<ContactController>()
    private val model by inject<ContactModel>()

    override val root = form {
        fieldset(
            text = "Novo contato",
            labelPosition = Orientation.VERTICAL
        ) {
            field("Nome") {
                textfield(model.name).required(
                    message = messages["validations.required"]
                )
            }
            field("Email") {
                textfield(model.email).validator { value ->
                    if (value == null)
                        error(messages["validations.required"])
                    else if (!value.isValidEmail())
                        error(messages["validations.invalid-email"])
                    else
                        null
                }
            }
            field("Telefone") {
                textfield(model.phone).required(
                    message = messages["validations.required"]
                )
            }
            field("Aniversário") {
                datepicker(model.birthDay).required(
                    message = messages["validations.required"]
                )
            }
        }

        hbox {
            button("Limpar") {
                hgrow = Priority.ALWAYS
                useMaxWidth = true

                action {
                    model.rollback()
                }
            }

            button("Salvar") {
                hgrow = Priority.ALWAYS
                useMaxWidth = true

                enableWhen(model.valid)

                action {
                    model.commit {
                        contactController.save(model.item)
                    }
                }
            }
        }

    }
}

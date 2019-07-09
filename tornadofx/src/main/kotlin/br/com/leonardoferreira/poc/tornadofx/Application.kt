package br.com.leonardoferreira.poc.tornadofx

import br.com.leonardoferreira.poc.tornadofx.view.MainView
import tornadofx.*
import java.util.Locale

class Application : App(MainView::class) {
    init {
        FX.locale = Locale("pt", "BR")
    }
}

fun main(args: Array<String>) {
    launch<Application>(args)
}

package br.com.leonardoferreira.poc.tornadofx

import br.com.leonardoferreira.poc.tornadofx.view.MainView
import tornadofx.*

class Application : App(
    primaryView = MainView::class
)

fun main(args: Array<String>) {
    launch<Application>(args)
}

package br.com.leonardoferreira.poc.tornadofx.extension

fun String.isValidEmail(): Boolean {
    return "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex().containsMatchIn(this)
}

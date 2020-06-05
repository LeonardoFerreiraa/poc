package br.com.leonardoferreira.poc.util

import java.util.logging.Level
import java.util.logging.Logger


sealed class Outcome<out T> {

    data class Success<out T>(val result: T) : Outcome<T>()

    data class Failure(val reason: String?, val cause: Throwable? = null) : Outcome<Nothing>()

    companion object {
        val log: Logger = Logger.getLogger(Outcome::class.java.name)
    }

}

inline fun <T, R> T.catching(block: T.() -> R): Outcome<R> =
    try {
        Outcome.Success(block())
    } catch (e: Throwable) {
        Outcome.log.log(Level.SEVERE, e.message, e)
        Outcome.Failure(e.message, e)
    }

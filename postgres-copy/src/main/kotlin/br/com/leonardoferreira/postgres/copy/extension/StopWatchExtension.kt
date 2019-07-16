package br.com.leonardoferreira.postgres.copy.extension

import org.springframework.util.StopWatch

fun runWithStopWatch(block: () -> Unit) {
    val stopWatch = StopWatch()

    stopWatch.start()
    block()
    stopWatch.stop()

    println(stopWatch.prettyPrint())
    println("seconds=${stopWatch.totalTimeSeconds}")
}

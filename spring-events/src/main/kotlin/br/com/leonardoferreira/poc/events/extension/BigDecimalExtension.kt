package br.com.leonardoferreira.poc.events.extension

import java.math.BigDecimal
import java.text.DecimalFormat

fun BigDecimal.moneyFormat(): String {
    val decimalFormat = DecimalFormat("$ #,###.00")
    return decimalFormat.format(this)
}

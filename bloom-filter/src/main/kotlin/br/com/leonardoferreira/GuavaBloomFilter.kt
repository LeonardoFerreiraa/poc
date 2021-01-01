package br.com.leonardoferreira

import com.google.common.annotations.Beta
import com.google.common.hash.BloomFilter
import com.google.common.hash.Funnel
import com.google.common.hash.Funnels
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Base64

fun main() {
    saveOnFile()
}

fun saveOnFile() {
    val bloomFilter = BloomFilter.create(
        Funnels.integerFunnel(),
        500,
        0.01
    )

    bloomFilter.put(1)
    bloomFilter.put(2)
    bloomFilter.put(3)
    
    bloomFilter.writeTo(FileOutputStream("/tmp/bloom-filter"))

    val bloomFilter2 = BloomFilter.readFrom(FileInputStream("/tmp/bloom-filter"), Funnels.integerFunnel())

    println(bloomFilter2.mightContain(1))
    println(bloomFilter2.mightContain(2))
    println(bloomFilter2.mightContain(3))
    println(bloomFilter2.mightContain(4))
}

fun saveOnBase64() {
    val bloomFilter = BloomFilter.create(
        Funnels.integerFunnel(),
        500,
        0.01
    )

    bloomFilter.put(1)
    bloomFilter.put(2)
    bloomFilter.put(3)

    val baos = ByteArrayOutputStream()
    bloomFilter.writeTo(baos)

    val bloomFilterBase64 = Base64.getEncoder().encodeToString(baos.toByteArray())

    val bais = ByteArrayInputStream(Base64.getDecoder().decode(bloomFilterBase64))
    val bloomFilter2 = BloomFilter.readFrom(bais, Funnels.integerFunnel())

    println(bloomFilter2.mightContain(1))
    println(bloomFilter2.mightContain(2))
    println(bloomFilter2.mightContain(3))
    println(bloomFilter2.mightContain(4))
}

fun simple() {
    val bloomFilter = BloomFilter.create(
        Funnels.integerFunnel(),
        500,
        0.01
    )

    bloomFilter.put(1)
    bloomFilter.put(2)
    bloomFilter.put(3)

    println(bloomFilter.mightContain(1))
    println(bloomFilter.mightContain(2))
    println(bloomFilter.mightContain(3))
    println(bloomFilter.mightContain(4))
}

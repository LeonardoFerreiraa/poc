package br.com.leonardoferreira

import java.util.BitSet
import kotlin.math.abs

fun main() {
    val bloomFilter = ManualBloomFilter<String>(10, 3)
    bloomFilter.add("a")
    bloomFilter.add("b")
    bloomFilter.add("c")
    bloomFilter.add("d")

    println(bloomFilter.mightContain("a"))
    println(bloomFilter.mightContain("b"))
    println(bloomFilter.mightContain("c"))
    println(bloomFilter.mightContain("d"))

    println(bloomFilter.mightContain("e"))
    println(bloomFilter.mightContain("f"))
    println(bloomFilter.mightContain("j"))
}

class ManualBloomFilter<T>(
    private val numBits: Int,
    private val numHashFunctions: Int
) {

    private val bits = BitSet(numBits)

    fun add(t: T) {
        val hash = hash(t)
        val hash2 = hash ushr 32

        for (i in 1..numHashFunctions) {
            val combinedHash = abs(hash + i * hash2)
            bits.set(combinedHash % numBits)
        }
    }

    fun mightContain(t: T): Boolean {
        val hash = hash(t)
        val hash2 = hash ushr 32

        for (i in 1..numHashFunctions) {
            val combinedHash = abs(hash + i * hash2)
            if (!bits.get(combinedHash % numBits)) {
                return false
            }
        }

        return true
    }

    private fun hash(t: T) =
        t.hashCode()
            .let { hashCode -> hashCode xor (hashCode ushr 16) }


}


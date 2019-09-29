package com.jbr.asharplibrary.random

import kotlin.random.Random

object RandomStringGenerator {

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generate(size: Int = 30): String = (1..size)
        .map { i -> Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")

    fun generateOrNull(size: Int = 30): String? =
        if (Random.nextBoolean()) {
            (1..size)
                .map { i -> Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
        } else {
            null
        }
}
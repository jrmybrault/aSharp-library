package com.jbr.asharplibrary.random

import java.util.*
import kotlin.math.abs
import kotlin.random.Random

object RandomDateGenerator {

    fun generate(): Date = Date(abs(System.currentTimeMillis() - Random.nextLong()))
}
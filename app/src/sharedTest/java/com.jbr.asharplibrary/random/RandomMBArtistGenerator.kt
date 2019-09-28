package com.jbr.asharplibrary.random

import com.jbr.asharplibrary.musicbrainz.dto.MBArtist
import kotlin.random.Random

object RandomMBArtistGenerator {

    fun generate(
        identifier: String = RandomStringGenerator.generate(),
        score: Int = Random.nextInt(),
        type: MBArtist.Type? = MBArtist.Type.values().random(),
        disambiguation: String? = RandomStringGenerator.generate(),
        name: String = RandomStringGenerator.generate(),
        sortName: String = RandomStringGenerator.generate(),
        tags: List<MBArtist.Tag>? = RandomMBArtistTagGenerator.generateList()
    ) = MBArtist(identifier, 0, type, "", "", "", emptyList())

    fun generateList(size: Int = 5): List<MBArtist> = (0..size).map { generate() }
}

object RandomMBArtistTagGenerator {

    fun generate(
        name: String = RandomStringGenerator.generate(),
        count: Int = Random.nextInt()
    ) = MBArtist.Tag(name, count)

    fun generateList(size: Int = 5): List<MBArtist.Tag> = (0..size).map { generate() }
}
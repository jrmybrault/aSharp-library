package com.jbr.asharplibrary.random

import com.jbr.musicbrainz.model.MBArtist
import kotlin.random.Random

object RandomMBArtistGenerator {

    fun generate(
        identifier: String = RandomStringGenerator.generate(),
        score: Int = Random.nextInt(),
        type: com.jbr.musicbrainz.model.MBArtist.Type? = com.jbr.musicbrainz.model.MBArtist.Type.values().random(),
        disambiguation: String? = RandomStringGenerator.generate(),
        name: String = RandomStringGenerator.generate(),
        sortName: String = RandomStringGenerator.generate(),
        tags: List<com.jbr.musicbrainz.model.MBArtist.Tag>? = RandomMBArtistTagGenerator.generateList()
    ) = com.jbr.musicbrainz.model.MBArtist(identifier, 0, type, "", "", "", emptyList())

    fun generateList(size: Int = 5): List<com.jbr.musicbrainz.model.MBArtist> = (0..size).map { generate() }
}

object RandomMBArtistTagGenerator {

    fun generate(
        name: String = RandomStringGenerator.generate(),
        count: Int = Random.nextInt()
    ) = com.jbr.musicbrainz.model.MBArtist.Tag(name, count)

    fun generateList(size: Int = 5): List<com.jbr.musicbrainz.model.MBArtist.Tag> = (0..size).map { generate() }
}
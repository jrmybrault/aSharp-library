package com.jbr.asharplibrary.random

import com.jbr.coredomain.ArtistIdentifier
import com.jbr.coredomain.searchartist.Artist
import com.jbr.coredomain.searchartist.ArtistType
import kotlin.random.Random

object RandomArtistGenerator {

    fun generate(
        identifier: ArtistIdentifier = RandomStringGenerator.generate(),
        name: String = RandomStringGenerator.generate(),
        type: ArtistType = ArtistType.values().random(),
        disambiguation: String? = RandomStringGenerator.generateOrNull(),
        sortName: String = RandomStringGenerator.generate(),
        score: Int = Random.nextInt(),
        tags: List<Artist.Tag> = RandomArtistTagGenerator.generateList()
    ) = Artist(identifier, name, type, disambiguation, sortName, score, tags)
}

object RandomArtistTagGenerator {

    fun generate(
        name: String = RandomStringGenerator.generate(),
        count: Int = Random.nextInt()
    ) = Artist.Tag(name, count)

    fun generateList(size: Int = 5): List<Artist.Tag> = (0..size).map { generate() }
}
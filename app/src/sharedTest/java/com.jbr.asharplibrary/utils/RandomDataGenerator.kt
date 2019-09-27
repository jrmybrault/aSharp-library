package com.jbr.asharplibrary.utils

import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistType
import com.jbr.asharplibrary.searchartist.ui.DisplayableFoundArtistItem
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import kotlin.random.Random

object RandomStringGenerator {

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generate(size: Int = 30): String = (1..size)
        .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")

    fun generateOrNull(size: Int = 30): String? =
        if (Random.nextBoolean()) {
            (1..size)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
        } else {
            null
        }
}

object RandomArtistGenerator {

    fun generate(
        identifier: ArtistIdentifier = RandomStringGenerator.generate(),
        name: String = RandomStringGenerator.generate(),
        type: ArtistType = ArtistType.SOLO,
        disambiguation: String? = RandomStringGenerator.generateOrNull(),
        sortName: String = RandomStringGenerator.generate(),
        score: Int = Random.nextInt(),
        tags: List<Artist.Tag> = emptyList()
    ) = Artist(identifier, name, type, disambiguation, sortName, score, tags)
}

object RandomArtistTagGenerator {

    fun generate(
        name: String = RandomStringGenerator.generate(),
        count: Int = Random.nextInt()
    ) = Artist.Tag(name, count)

    fun generateList(size: Int = 5): List<Artist.Tag> = (1..size).map { generate() }
}

object RandomDisplayableFoundArtistItemGenerator {

    fun generate(
        identifier: String = RandomStringGenerator.generate(),
        name: String = RandomStringGenerator.generate(),
        typeText: String = RandomStringGenerator.generate(),
        tagsText: String? = RandomStringGenerator.generateOrNull(),
        shouldDisplayTags: Boolean = Random.nextBoolean()
    ): DisplayableFoundArtistItem = DisplayableFoundArtistItem(
        identifier = identifier,
        name = name,
        typeText = typeText,
        tagsText = tagsText,
        shouldDisplayTags = shouldDisplayTags
    )
}
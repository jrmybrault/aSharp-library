package com.jbr.asharplibrary.random

import com.jbr.asharplibrary.searchartist.ui.DisplayableFoundArtistItem
import kotlin.random.Random

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
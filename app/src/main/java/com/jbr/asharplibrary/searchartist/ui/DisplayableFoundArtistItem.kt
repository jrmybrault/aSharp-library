package com.jbr.asharplibrary.searchartist.ui

import android.content.res.Resources
import com.jbr.asharplibrary.shared.ui.displayTextId
import com.jbr.coredomain.ArtistIdentifier
import com.jbr.coredomain.searchartist.Artist

data class DisplayableFoundArtistItem(
    val identifier: ArtistIdentifier,
    val name: String,
    val typeText: String,
    val tagsText: String?,
    val shouldDisplayTags: Boolean = !tagsText.isNullOrEmpty()
) {

    constructor(artist: Artist, resources: Resources) : this(
        identifier = artist.identifier,
        name = artist.sortName,
        typeText = artist.disambiguatedTypeDisplayText(resources),
        tagsText = tagsDisplayText(artist.tags, MAX_TAG_NUMBER)
    )

    private companion object {
        private const val MAX_TAG_NUMBER = 5

        private fun tagsDisplayText(tags: List<Artist.Tag>?, maxTagNumber: Int): String? = if (tags.isNullOrEmpty()) {
            null
        } else {
            tags.sortedByDescending { it.count }.map { "#${it.name}" }.take(maxTagNumber).joinToString(" ")
        }
    }
}

private fun Artist.disambiguatedTypeDisplayText(resources: Resources): String {
    val typeStringId = type.displayTextId()
    val typeStringText = resources.getString(typeStringId)

    return if (disambiguation != null) {
        "$typeStringText - $disambiguation"
    } else {
        typeStringText
    }
}


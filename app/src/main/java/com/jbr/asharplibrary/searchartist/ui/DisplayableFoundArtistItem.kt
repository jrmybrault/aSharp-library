package com.jbr.asharplibrary.searchartist.ui

import android.content.res.Resources
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.shared.ui.displayTextId
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier

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
        tagsText = artist.tagsDisplayText(MAX_TAG_NUMBER)
    )

    companion object {
        const val MAX_TAG_NUMBER = 5
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

private fun Artist.tagsDisplayText(maxTagNumber: Int): String? = if (tags.isNullOrEmpty()) {
    null
} else {
    tags.sortedByDescending { it.count }.map { "#${it.name}" }.take(maxTagNumber).joinToString(" ")
}

package com.jbr.asharplibrary.searchartist.ui

import android.content.res.Resources
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.shared.ui.displayTextId
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier

data class DisplayableFoundArtistItem(
    val identifier: ArtistIdentifier,
    val name: String,
    val disambiguatedTypeText: String
) {

    constructor(artist: Artist, resources: Resources) : this(
        identifier = artist.identifier,
        name = artist.sortName,
        disambiguatedTypeText = artist.disambiguatedTypeDisplayText(resources)
    )
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

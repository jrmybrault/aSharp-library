package com.jbr.asharplibrary.searchartist.ui

import android.content.res.Resources
import androidx.recyclerview.widget.DiffUtil
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

class DisplayableFoundArtistItemDiffCallback : DiffUtil.ItemCallback<DisplayableFoundArtistItem>() {

    override fun areItemsTheSame(oldItem: DisplayableFoundArtistItem, newItem: DisplayableFoundArtistItem): Boolean {
        return oldItem.identifier == newItem.identifier
    }

    override fun areContentsTheSame(oldItem: DisplayableFoundArtistItem, newItem: DisplayableFoundArtistItem): Boolean {
        return oldItem == newItem
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

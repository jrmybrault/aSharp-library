package com.jbr.asharplibrary.searchartist.ui

import android.content.res.Resources
import androidx.recyclerview.widget.DiffUtil
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.shared.ui.displayTextId
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier

data class DisplayableFoundArtistItem(
    val identifier: ArtistIdentifier,
    val name: String,
    val disambiguatedTypeText: String,
    val tagsText: String?,
    val shouldDisplayTags: Boolean = !tagsText.isNullOrEmpty()
) {

    constructor(artist: Artist, resources: Resources) : this(
        identifier = artist.identifier,
        name = artist.sortName,
        disambiguatedTypeText = artist.disambiguatedTypeDisplayText(resources),
        tagsText = artist.tagsDisplayText(MAX_TAG_NUMBER)
    )

    companion object {
        private const val MAX_TAG_NUMBER = 5
    }
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

private fun Artist.tagsDisplayText(maxTagNumber: Int): String? {
    return tags?.sortedByDescending { it.count }?.map { "#${it.name}" }?.take(maxTagNumber)?.joinToString(" ")
}

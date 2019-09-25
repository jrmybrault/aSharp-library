package com.jbr.asharplibrary.artistdetails.ui.discography

import android.content.res.Resources
import android.net.Uri
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.artistdetails.domain.Release
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class DisplayableArtistReleaseItem(
    val identifier: String,
    val title: String,
    val releaseYearText: String,
    val frontCoverUri: Uri
) {

    constructor(release: Release, resources: Resources) : this(
        identifier = release.identifier,
        title = release.displayTitle(resources),
        releaseYearText = releaseDateFormatter.format(release.releaseDate),
        frontCoverUri = frontCoverUri(release.identifier)
    )

    companion object {

        private const val frontCoverBaseUrl = "https://coverartarchive.org/release-group/%s/front.jpg-250"
        private val releaseDateFormatter: DateFormat = SimpleDateFormat("yyyy", Locale.getDefault())

        fun frontCoverUri(identifier: String): Uri {
            return String.format(frontCoverBaseUrl, identifier).toUri()
        }
    }
}

fun Release.displayTitle(resources: Resources): String {
    val primaryTypeSuffixId = primaryType.displaySuffixId()
    val primaryTypeSuffixText = if (primaryTypeSuffixId != null) {
        " ${resources.getString(primaryTypeSuffixId)} "
    } else {
        ""
    }
    val secondaryTypeSuffixId = secondaryTypes.firstOrNull()?.displaySuffixId()
    val secondaryTypeSuffixText = if (secondaryTypeSuffixId != null) {
        " ${resources.getString(secondaryTypeSuffixId)} "
    } else {
        ""
    }

    return "$title$primaryTypeSuffixText$secondaryTypeSuffixText"
}

fun Release.PrimaryType.displaySuffixId(): Int? {
    return when (this) {
        Release.PrimaryType.SINGLE -> R.string.artist_details_release_primary_suffix_single
        Release.PrimaryType.EP -> R.string.artist_details_release_primary_suffix_ep
        else -> null
    }
}

fun Release.SecondaryType.displaySuffixId(): Int? {
    return when (this) {
        Release.SecondaryType.COMPILATION -> R.string.artist_details_release_secondary_suffix_compilation
        Release.SecondaryType.LIVE -> R.string.artist_details_release_secondary_suffix_live
        else -> null
    }
}

class DisplayableArtistReleaseItemDiffCallback : DiffUtil.ItemCallback<DisplayableArtistReleaseItem>() {

    override fun areItemsTheSame(oldItem: DisplayableArtistReleaseItem, newItem: DisplayableArtistReleaseItem): Boolean {
        return oldItem.identifier == newItem.identifier
    }

    override fun areContentsTheSame(oldItem: DisplayableArtistReleaseItem, newItem: DisplayableArtistReleaseItem): Boolean {
        return oldItem == newItem
    }
}
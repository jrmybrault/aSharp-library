package com.jbr.asharplibrary.artistdetails.ui.discography

import android.content.res.Resources
import android.net.Uri
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import com.jbr.asharplibrary.R
import com.jbr.coredomain.artistdetails.Release
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

//region - DisplayableArtistReleaseItemType

sealed class DisplayableArtistReleaseItemType

class DisplayableArtistReleaseItemTypeDiffCallback : DiffUtil.ItemCallback<DisplayableArtistReleaseItemType>() {

    override fun areItemsTheSame(oldItem: DisplayableArtistReleaseItemType, newItem: DisplayableArtistReleaseItemType): Boolean {
        return if (oldItem is DisplayableReleaseCategoryItem && newItem is DisplayableReleaseCategoryItem) {
            oldItem.categoryNameId == newItem.categoryNameId
        } else if (oldItem is DisplayableReleaseInfoItem && newItem is DisplayableReleaseInfoItem) {
            oldItem.identifier == newItem.identifier
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: DisplayableArtistReleaseItemType, newItem: DisplayableArtistReleaseItemType): Boolean {
        return if (oldItem is DisplayableReleaseCategoryItem && newItem is DisplayableReleaseCategoryItem) {
            oldItem.equals(newItem)
        } else if (oldItem is DisplayableReleaseInfoItem && newItem is DisplayableReleaseInfoItem) {
            oldItem.equals(newItem)
        } else {
            false
        }
    }
}

//endregion

//region - DisplayableReleaseCategoryItem

data class DisplayableReleaseCategoryItem(val categoryNameId: Int) : DisplayableArtistReleaseItemType() {

    constructor(type: Release.PrimaryType) : this(categoryNameId = type.displayNameId())
}

private fun Release.PrimaryType.displayNameId(): Int {
    return when (this) {
        Release.PrimaryType.ALBUM -> R.string.artist_details_release_primary_type_album
        Release.PrimaryType.SINGLE -> R.string.artist_details_release_primary_type_single
        Release.PrimaryType.EP -> R.string.artist_details_release_primary_type_ep
        Release.PrimaryType.OTHER -> R.string.artist_details_release_primary_type_other
    }
}

//endregion

//region - DisplayableReleaseInfoItem

data class DisplayableReleaseInfoItem(
    val identifier: String,
    val title: String,
    val releaseYearText: String,
    val smallFrontCoverUri: Uri,
    val largeFrontCoverUri: Uri
) : DisplayableArtistReleaseItemType() {

    constructor(release: Release, resources: Resources) : this(
        identifier = release.identifier,
        title = release.displayTitle(resources),
        releaseYearText = releaseDateFormatter.format(release.releaseDate),
        smallFrontCoverUri = frontCoverUri(release.identifier, CoverType.SMALL),
        largeFrontCoverUri = frontCoverUri(release.identifier, CoverType.LARGE)
    )

    enum class CoverType(val size: Int) {

        SMALL(size = 250),
        LARGE(size = 500)
    }

    companion object {

        private const val FRONT_COVER_BASE_URL = "https://coverartarchive.org/release-group/%s/front.jpg-%s"

        private val releaseDateFormatter: DateFormat = SimpleDateFormat("yyyy", Locale.getDefault())

        fun frontCoverUri(identifier: String, type: CoverType): Uri {
            return String.format(FRONT_COVER_BASE_URL, identifier, type.size).toUri()
        }
    }
}

private fun Release.displayTitle(resources: Resources): String {
    val secondaryTypeTextId = secondaryTypes.getOrNull(0).displaySuffixId()
    val secondaryTypeText = if (secondaryTypeTextId != null) {
        " ${resources.getString(secondaryTypeTextId)} "
    } else {
        ""
    }

    return "$title$secondaryTypeText"
}

private fun Release.SecondaryType?.displaySuffixId(): Int? {
    return when (this) {
        Release.SecondaryType.COMPILATION -> R.string.artist_details_release_secondary_type_compilation
        Release.SecondaryType.SOUNDTRACK -> R.string.artist_details_release_secondary_type_soundtrack
        Release.SecondaryType.LIVE -> R.string.artist_details_release_secondary_type_live
        else -> null
    }
}

//endregion
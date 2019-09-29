package com.jbr.asharplibrary.artistdetails.ui.about

import android.content.res.Resources
import android.text.Spanned
import androidx.core.text.parseAsHtml
import com.jbr.asharplibrary.R
import com.jbr.coredomain.artistdetails.DetailedArtist
import com.jbr.coredomain.searchartist.ArtistType
import java.text.DateFormat
import java.util.*

internal data class DisplayableArtistAbout(
    val genderText: String?,
    val shouldDisplayGender: Boolean = genderText != null,
    val lifeSpanBeginningTitleText: String?,
    val lifeSpanBeginningText: String?,
    val shouldDisplayLifeSpanBegin: Boolean = !lifeSpanBeginningTitleText.isNullOrEmpty() && !lifeSpanBeginningText.isNullOrEmpty(),
    val lifeSpanEndTitleText: String?,
    val lifeSpanEndText: String?,
    val shouldDisplayLifeSpanEnd: Boolean = !lifeSpanEndTitleText.isNullOrEmpty() && !lifeSpanEndText.isNullOrEmpty(),
    val countryText: String,
    val ipiCodesText: String,
    val isniCodesText: String,
    val shouldDisplayLegalCodes: Boolean = ipiCodesText.isNotEmpty() || isniCodesText.isNotEmpty(),
    val wikipediaExtractText: Spanned?,
    val ratingsText: String?
) {

    constructor(artist: DetailedArtist, resources: Resources) : this(
        genderText = artist.gender.displayText(resources),
        lifeSpanBeginningTitleText = artist.lifeSpanBeginningTitleDisplayText(resources),
        lifeSpanBeginningText = artist.lifeSpanBeginningDisplayText(resources, lifeSpanBeginningFormatter),
        lifeSpanEndTitleText = artist.lifeSpanEndTitleDisplayText(resources),
        lifeSpanEndText = lifeSpanEndDisplayText(artist.lifeSpanBeginning, artist.lifeSpanEnd, resources),
        countryText = artist.countryName ?: "-",
        ipiCodesText = artist.ipiCodesDisplayText(),
        isniCodesText = artist.isniCodesDisplayText(),
        wikipediaExtractText = artist.wikipediaExtract?.parseAsHtml(),
        ratingsText = ratingsDisplayText(artist.rating, resources)
    )

    private companion object {
        private val lifeSpanBeginningFormatter: DateFormat = DateFormat.getDateInstance()

        private fun lifeSpanEndDisplayText(lifeSpanBeginning: Date?, lifeSpanEnd: Date?, resources: Resources): String? {
            if (lifeSpanEnd == null) {
                return null
            }

            val lifeSpanEndText = lifeSpanBeginningFormatter.format(lifeSpanEnd)

            val lifeSpanDuration = if (lifeSpanBeginning != null) {
                com.jbr.sharedfoundation.numberOfYearsBetween(lifeSpanBeginning!!, lifeSpanEnd!!)
            } else {
                0
            }

            return if (lifeSpanDuration > 0) {
                "$lifeSpanEndText ${resources.getString(R.string.artist_details_life_span_duration, lifeSpanDuration)}"
            } else {
                lifeSpanEndText
            }
        }

        private fun ratingsDisplayText(rating: DetailedArtist.Rating?, resources: Resources): String? {
            return if (rating == null || rating.count == 0) {
                resources.getString(R.string.artist_details_ratings_none)
            } else {
                resources.getString(R.string.artist_details_ratings_value, String.format("%.1f", rating.averageValue), rating.count)
            }
        }
    }
}

private fun DetailedArtist.Gender?.displayText(resources: Resources): String? {
    return when (this) {
        DetailedArtist.Gender.MALE -> resources.getString(R.string.artist_details_gender_male)
        DetailedArtist.Gender.FEMALE -> resources.getString(R.string.artist_details_gender_female)
        else -> null
    }
}

private fun DetailedArtist.lifeSpanBeginningTitleDisplayText(resources: Resources): String? {
    val textId = when (type) {
        ArtistType.SOLO -> R.string.artist_details_life_span_begin_solo
        ArtistType.BAND -> R.string.artist_details_life_span_begin_band
        ArtistType.OTHER -> null
    }

    return if (textId != null) {
        resources.getString(textId)
    } else {
        null
    }
}

private fun DetailedArtist.lifeSpanBeginningDisplayText(resources: Resources, dateFormatter: DateFormat): String? {
    return if (lifeSpanBeginning != null) {
        resources.getString(
            R.string.artist_details_life_span_begin_value,
            dateFormatter.format(lifeSpanBeginning),
            beginningArea ?: "-"
        )
    } else {
        null
    }
}

private fun DetailedArtist.lifeSpanEndTitleDisplayText(resources: Resources): String? {
    val textId = when (type) {
        ArtistType.SOLO -> R.string.artist_details_life_span_end_solo
        ArtistType.BAND -> R.string.artist_details_life_span_end_band
        ArtistType.OTHER -> null
    }

    return if (textId != null) {
        resources.getString(textId)
    } else {
        null
    }
}

private fun DetailedArtist.ipiCodesDisplayText(): String {
    val ipiCodesText = ipiCodes.joinToString("\n")

    return if (ipiCodesText.isEmpty()) {
        "-"
    } else {
        ipiCodesText
    }
}

private fun DetailedArtist.isniCodesDisplayText(): String {
    val isniCodesText = isniCodes.joinToString("\n")

    return if (isniCodesText.isEmpty()) {
        "-"
    } else {
        isniCodesText
    }
}

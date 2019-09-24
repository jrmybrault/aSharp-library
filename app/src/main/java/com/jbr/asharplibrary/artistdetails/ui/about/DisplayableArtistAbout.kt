package com.jbr.asharplibrary.artistdetails.ui.about

import android.content.res.Resources
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.artistdetails.domain.DetailedArtist
import com.jbr.asharplibrary.searchartist.domain.ArtistType
import com.jbr.utils.numberOfYearsBetween
import java.text.DateFormat

data class DisplayableArtistAbout(
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
    val shouldDisplayIpiCodes: Boolean = ipiCodesText.isNotEmpty(),
    val isniCodesText: String,
    val shouldDisplayIsniCodes: Boolean = isniCodesText.isNotEmpty(),
    val wikipediaExtractText: String?
) {

    constructor(artist: DetailedArtist, resources: Resources) : this(
        genderText = artist.gender.displayText(resources),
        lifeSpanBeginningTitleText = artist.lifeSpanBeginningTitleDisplayText(resources),
        lifeSpanBeginningText = artist.lifeSpanBeginningDisplayText(
            resources,
            lifeSpanBeginningFormatter
        ),
        lifeSpanEndTitleText = artist.lifeSpanEndTitleDisplayText(resources),
        lifeSpanEndText = artist.lifeSpanEndDisplayText(
            resources,
            lifeSpanBeginningFormatter
        ),
        countryText = artist.countryName ?: "-",
        ipiCodesText = artist.ipiCodes.joinToString("\n"),
        isniCodesText = artist.isniCodes.joinToString("\n"),
        wikipediaExtractText = artist.wikipediaExtract
    )

    companion object {
        private val lifeSpanBeginningFormatter: DateFormat = DateFormat.getDateInstance()
    }
}

fun DetailedArtist.Gender?.displayText(resources: Resources): String? {
    return when (this) {
        DetailedArtist.Gender.MALE -> resources.getString(R.string.artist_details_gender_male)
        DetailedArtist.Gender.FEMALE -> resources.getString(R.string.artist_details_gender_female)
        else -> null
    }
}

fun DetailedArtist.lifeSpanBeginningTitleDisplayText(resources: Resources): String? {
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

fun DetailedArtist.lifeSpanBeginningDisplayText(resources: Resources, dateFormatter: DateFormat): String? {
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

fun DetailedArtist.lifeSpanEndTitleDisplayText(resources: Resources): String? {
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

fun DetailedArtist.lifeSpanEndDisplayText(resources: Resources, dateFormatter: DateFormat): String? {
    if (lifeSpanEnd == null) {
        return null
    }

    val lifeSpanDuration = if (lifeSpanBeginning != null) {
        numberOfYearsBetween(lifeSpanBeginning, lifeSpanEnd)
    } else {
        0
    }

    return if (lifeSpanDuration > 0) {
        resources.getString(R.string.artist_details_life_span_duration, lifeSpanDuration)
    } else {
        dateFormatter.format(lifeSpanEnd)
    }
}

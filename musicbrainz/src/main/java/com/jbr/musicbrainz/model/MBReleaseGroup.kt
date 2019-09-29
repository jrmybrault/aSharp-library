package com.jbr.musicbrainz.model

import com.google.gson.annotations.SerializedName
import com.jbr.coredomain.artistdetails.Release
import java.util.*

data class MBReleaseGroup(
    @SerializedName("id") val identifier: String,
    val title: String,
    @SerializedName("first-release-date") val firstReleaseDate: Date,
    @SerializedName("primary-type") val primaryType: PrimaryType,
    @SerializedName("secondary-types") val secondaryTypes: List<SecondaryType>
) {

    enum class PrimaryType {

        @SerializedName("Album")
        ALBUM,
        @SerializedName("Single")
        SINGLE,
        @SerializedName("Ep")
        EP,
        @SerializedName("Broadcast")
        BROADCAST,
        @SerializedName("Other")
        OTHER
    }
    
    enum class SecondaryType {

        @SerializedName("Compilation")
        COMPILATION,
        @SerializedName("Soundtrack")
        SOUNDTRACK,
        @SerializedName("Spokenword")
        SPOKEN_WORD,
        @SerializedName("Interview")
        INTERVIEW,
        @SerializedName("Audiobook")
        AUDIO_BOOK,
        @SerializedName("Audio drama")
        AUDIO_DRAMA,
        @SerializedName("Live")
        LIVE,
        @SerializedName("Remix")
        REMIX,
        @SerializedName("DJ-mix")
        DJ_MIX,
        @SerializedName("Mixtape/Street")
        MIXTAPE
    }
}

fun MBReleaseGroup.asDomain(): Release {
    return Release(
        identifier = identifier,
        title = title,
        releaseDate = firstReleaseDate,
        primaryType = primaryType.asDomain(),
        secondaryTypes = secondaryTypes.map { it.asDomain() }
    )
}

fun MBReleaseGroup.PrimaryType.asDomain(): Release.PrimaryType {
    return when (this) {
        MBReleaseGroup.PrimaryType.ALBUM -> Release.PrimaryType.ALBUM
        MBReleaseGroup.PrimaryType.SINGLE -> Release.PrimaryType.SINGLE
        MBReleaseGroup.PrimaryType.EP -> Release.PrimaryType.EP
        else -> Release.PrimaryType.OTHER
    }
}

fun MBReleaseGroup.SecondaryType.asDomain(): Release.SecondaryType {
    return when (this) {
        MBReleaseGroup.SecondaryType.COMPILATION -> Release.SecondaryType.COMPILATION
        MBReleaseGroup.SecondaryType.SOUNDTRACK -> Release.SecondaryType.SOUNDTRACK
        MBReleaseGroup.SecondaryType.LIVE -> Release.SecondaryType.LIVE
        else -> Release.SecondaryType.OTHER
    }
}
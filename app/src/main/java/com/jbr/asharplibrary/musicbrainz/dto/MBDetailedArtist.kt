package com.jbr.asharplibrary.musicbrainz.dto

import com.google.gson.annotations.SerializedName

data class MBDetailedArtist(
    @SerializedName("id") val identifier: String,
    val type: MBArtistType?,
    val disambiguation: String?,
    val name: String,
    val area: Area,
    @SerializedName("begin_area")
    val beginArea: BeginArea,
    val gender: Gender?,
    @SerializedName("life-span")
    val lifeSpan: LifeSpan,
    @SerializedName("release-groups")
    val releaseGroups: List<MBReleaseGroup> = emptyList()
) {

    enum class Gender {

        @SerializedName("male")
        MALE,
        @SerializedName("female")
        FEMALE
    }

    data class Area(val name: String)

    data class BeginArea(val name: String)

    data class LifeSpan(val begin: String?, val end: String?)
}



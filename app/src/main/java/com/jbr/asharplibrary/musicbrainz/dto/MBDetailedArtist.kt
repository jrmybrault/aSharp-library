package com.jbr.asharplibrary.musicbrainz.dto

import com.google.gson.annotations.SerializedName
import com.jbr.asharplibrary.artistdetails.domain.DetailedArtist
import java.util.*

data class MBDetailedArtist(
    @SerializedName("id") val identifier: String,
    val type: MBArtist.Type?,
    val disambiguation: String?,
    val name: String,
    val gender: Gender?,
    @SerializedName("ipis") val ipiCodes: List<String> = emptyList(),
    @SerializedName("isnis") val isniCodes: List<String> = emptyList(),
    val area: Area,
    @SerializedName("begin_area")
    val beginningArea: BeginArea,
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

    data class LifeSpan(@SerializedName("begin") val beginning: Date, val end: Date?)
}

fun MBDetailedArtist.asDomain(): DetailedArtist {
    return DetailedArtist(
        identifier = identifier,
        type = type.asDomain(),
        typePrecision = disambiguation,
        name = name,
        gender = gender?.asDomain(),
        ipiCodes = ipiCodes,
        isniCodes = isniCodes,
        countryName = area.name,
        beginningArea = beginningArea.name,
        lifeSpanBeginning = lifeSpan.beginning,
        lifeSpanEnd = lifeSpan.end
    )
}

fun MBDetailedArtist.Gender.asDomain(): DetailedArtist.Gender {
    return when (this) {
        MBDetailedArtist.Gender.MALE -> DetailedArtist.Gender.MALE
        MBDetailedArtist.Gender.FEMALE -> DetailedArtist.Gender.FEMALE
    }
}

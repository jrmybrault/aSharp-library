package com.jbr.asharplibrary.musicbrainz.dto

import com.jbr.asharplibrary.searchartist.domain.Artist
import com.squareup.moshi.Json

data class MBArtist(
    @Json(name = "id") val identifier: String,
    val type: MBArtistType?,
    val name: String,
    @Json(name = "sort-name") val sortName: String,
    val gender: MBArtistGender?,
    @Json(name = "country") val countryCode: String?,
    @Json(name = "life-span") val lifeSpan: MBArtistLifeSpan,
    val tags: List<MBArtistTag> = emptyList()
)

enum class MBArtistType {

    @Json(name = "Person")
    PERSON,
    @Json(name = "Group")
    GROUP,
    @Json(name = "Orchestra")
    ORCHESTRA,
    @Json(name = "Choir")
    CHOIR,
    @Json(name = "Character")
    CHARACTER,
    @Json(name = "Other")
    OTHER
}

enum class MBArtistGender {

    @Json(name = "male")
    MALE,
    @Json(name = "female")
    FEMALE
}

data class MBArtistLifeSpan(val begin: String?, val end: String?)

data class MBArtistTag(val name: String)


fun MBArtist.asDomain(): Artist {
    return Artist(name = name)
}
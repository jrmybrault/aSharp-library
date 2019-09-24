package com.jbr.asharplibrary.musicbrainz.dto

import com.google.gson.annotations.SerializedName
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistType

data class MBArtist(
    @SerializedName("id") val identifier: String,
    val score: Int,
    val type: MBArtistType?,
    val name: String,
    @SerializedName("sort-name") val sortName: String,
    val tags: List<MBArtistTag> = emptyList()
)

enum class MBArtistType {

    @SerializedName("Person")
    PERSON,
    @SerializedName("Group")
    GROUP,
    @SerializedName("Orchestra")
    ORCHESTRA,
    @SerializedName("Choir")
    CHOIR,
    @SerializedName("Character")
    CHARACTER,
    @SerializedName("Other")
    OTHER
}

data class MBArtistTag(val name: String)

fun MBArtist.asDomain(): Artist {
    return Artist(
        identifier = identifier,
        name = name,
        type = type?.asDomain() ?: ArtistType.OTHER,
        sortName = sortName,
        score = score
    )
}

fun MBArtistType.asDomain(): ArtistType {
    return when (this) {
        MBArtistType.PERSON -> ArtistType.SOLO
        MBArtistType.GROUP -> ArtistType.BAND
        MBArtistType.ORCHESTRA -> ArtistType.BAND
        MBArtistType.CHOIR -> ArtistType.BAND
        MBArtistType.CHARACTER -> ArtistType.SOLO
        MBArtistType.OTHER -> ArtistType.OTHER
    }
}
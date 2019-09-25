package com.jbr.asharplibrary.musicbrainz.dto

import com.google.gson.annotations.SerializedName
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistType

data class MBArtist(
    @SerializedName("id") val identifier: String,
    val score: Int,
    val type: Type?,
    val disambiguation: String?,
    val name: String,
    @SerializedName("sort-name") val sortName: String,
    val tags: List<Tag> = emptyList()
) {

    enum class Type {

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

    data class Tag(val name: String)
}


fun MBArtist.asDomain(): Artist {
    return Artist(
        identifier = identifier,
        name = name,
        type = type.asDomain(),
        disambiguation = disambiguation,
        sortName = sortName,
        score = score
    )
}

fun MBArtist.Type?.asDomain(): ArtistType {
    return when (this) {
        MBArtist.Type.PERSON -> ArtistType.SOLO
        MBArtist.Type.GROUP -> ArtistType.BAND
        MBArtist.Type.ORCHESTRA -> ArtistType.BAND
        MBArtist.Type.CHOIR -> ArtistType.BAND
        MBArtist.Type.CHARACTER -> ArtistType.SOLO
        MBArtist.Type.OTHER -> ArtistType.OTHER
        null -> ArtistType.OTHER
    }
}
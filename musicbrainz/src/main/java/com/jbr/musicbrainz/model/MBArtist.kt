package com.jbr.musicbrainz.model

import com.google.gson.annotations.SerializedName
import com.jbr.coredomain.searchartist.Artist
import com.jbr.coredomain.searchartist.ArtistType

data class MBArtist(
    @SerializedName("id") val identifier: String,
    val score: Int,
    val type: Type?,
    val disambiguation: String?,
    val name: String,
    @SerializedName("sort-name") val sortName: String,
    val tags: List<Tag>?
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

    data class Tag(val name: String, val count: Int)
}


fun MBArtist.asDomain(): Artist {
    return Artist(
        identifier = identifier,
        name = name,
        type = type.asDomain(),
        disambiguation = disambiguation,
        sortName = sortName,
        score = score,
        tags = tags?.map { it.asDomain() }
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

fun MBArtist.Tag.asDomain(): Artist.Tag {
    return Artist.Tag(name = name, count = count)
}
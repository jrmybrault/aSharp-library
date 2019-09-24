package com.jbr.asharplibrary.artistdetails.domain

import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import java.util.*

data class DetailedArtist(
    val identifier: ArtistIdentifier,
    val name: String,
    val countryName: String,
    val areaBegin: String?,
    val lifeSpanBegin: Date,
    val lifeSpanEnd: Date?,
    val typeDetail: String?,
    val gender: Gender?
)

enum class Gender {

    MALE,
    FEMALE
}
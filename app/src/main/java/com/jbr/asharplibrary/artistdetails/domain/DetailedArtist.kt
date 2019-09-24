package com.jbr.asharplibrary.artistdetails.domain

import com.jbr.asharplibrary.searchartist.domain.ArtistType
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import java.util.*

data class DetailedArtist(
    val identifier: ArtistIdentifier,
    val type: ArtistType,
    val typePrecision: String?,
    val name: String,
    val gender: Gender?,
    val ipiCodes: List<String>,
    val isniCodes: List<String>,
    val countryName: String,
    val beginningArea: String?,
    val lifeSpanBeginning: Date,
    val lifeSpanEnd: Date?,
    var wikipediaExtract: String? = null
) {

    enum class Gender {

        MALE,
        FEMALE
    }
}

package com.jbr.coredomain.artistdetails

import com.jbr.coredomain.ArtistIdentifier
import com.jbr.coredomain.searchartist.ArtistType
import java.util.*

data class DetailedArtist(
    val identifier: ArtistIdentifier,
    val type: ArtistType,
    val typePrecision: String?,
    val name: String,
    val gender: Gender?,
    val ipiCodes: List<String>,
    val isniCodes: List<String>,
    val countryName: String?,
    val beginningArea: String?,
    val lifeSpanBeginning: Date?,
    val lifeSpanEnd: Date?,
    var wikipediaExtract: String? = null,
    val releases: List<Release> = emptyList(),
    val rating: Rating?
) {

    enum class Gender {

        MALE,
        FEMALE
    }

    data class Rating(val averageValue: Float, val count: Int)
}

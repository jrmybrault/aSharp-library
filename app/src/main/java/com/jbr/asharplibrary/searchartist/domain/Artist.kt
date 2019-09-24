package com.jbr.asharplibrary.searchartist.domain

import com.jbr.asharplibrary.shareddomain.ArtistIdentifier

data class Artist(val identifier: ArtistIdentifier, val name: String, val type: ArtistType, val sortName: String, val score: Int)

enum class ArtistType {

    SOLO,
    BAND,
    OTHER
}
package com.jbr.asharplibrary.searchartist.domain

data class Artist(val name: String, val type: ArtistType, val sortName: String, val score: Int)

enum class ArtistType {

    SOLO,
    BAND,
    OTHER
}
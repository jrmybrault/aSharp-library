package com.jbr.asharplibrary.searchartist.domain

data class Artist(val name: String, val type: ArtistType)

enum class ArtistType {

    SOLO,
    BAND,
    OTHER
}
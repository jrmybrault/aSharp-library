package com.jbr.coredomain.searchartist

import com.jbr.coredomain.ArtistIdentifier

data class Artist(
    val identifier: ArtistIdentifier,
    val name: String,
    val type: ArtistType,
    val disambiguation: String? = null,
    val sortName: String,
    val score: Int,
    val tags: List<Tag>? = emptyList()
) {

    data class Tag(val name: String, val count: Int)
}

package com.jbr.asharplibrary.searchartist.ui

import com.jbr.asharplibrary.searchartist.domain.Artist

data class DisplayableFoundArtistItem(val name: String) {

    constructor(artist: Artist) : this(name = artist.name)
}
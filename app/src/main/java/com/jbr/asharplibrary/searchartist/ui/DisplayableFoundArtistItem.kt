package com.jbr.asharplibrary.searchartist.ui

import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.shared.ui.displayTextId

data class DisplayableFoundArtistItem(val name: String, val typeStringId: Int) {


    constructor(artist: Artist) : this(name = artist.name, typeStringId = artist.type.displayTextId())
}

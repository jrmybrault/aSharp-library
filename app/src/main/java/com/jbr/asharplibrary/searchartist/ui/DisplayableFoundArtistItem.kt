package com.jbr.asharplibrary.searchartist.ui

import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.shared.ui.displayTextId
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier

data class DisplayableFoundArtistItem(val identifier: ArtistIdentifier, val name: String, val typeStringId: Int) {


    constructor(artist: Artist) : this(identifier = artist.identifier, name = artist.sortName, typeStringId = artist.type.displayTextId())
}

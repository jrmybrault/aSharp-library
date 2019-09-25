package com.jbr.asharplibrary.searchartist.domain

import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import java.util.*

data class PreviousArtistSearch(val artist: Artist, val identifier: ArtistIdentifier = artist.identifier, val date: Date = Date())
package com.jbr.asharplibrary.searchartist.domain

import com.jbr.asharplibrary.shareddomain.SearchIdentifier
import java.util.*

data class PreviousArtistSearch(val artist: Artist, val identifier: SearchIdentifier = artist.identifier, val date: Date = Date())
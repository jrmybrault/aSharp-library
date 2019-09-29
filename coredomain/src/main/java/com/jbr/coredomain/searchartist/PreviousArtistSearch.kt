package com.jbr.coredomain.searchartist

import com.jbr.coredomain.SearchIdentifier
import java.util.*

data class PreviousArtistSearch(val artist: Artist, val identifier: SearchIdentifier = artist.identifier, val date: Date = Date())
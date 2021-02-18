package com.jbr.asharplibrary.random

import com.jbr.coredomain.searchartist.Artist
import com.jbr.coredomain.searchartist.PreviousArtistSearch
import java.util.*

object RandomPreviousArtistSearchGenerator {

    fun generate(
        artist: Artist = RandomArtistGenerator.generate(),
        date: Date = RandomDateGenerator.generate()
    ) = PreviousArtistSearch(artist = artist, date = date)
}

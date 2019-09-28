package com.jbr.asharplibrary.random

import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.PreviousArtistSearch
import java.util.*

object RandomPreviousArtistSearchGenerator {

    fun generate(
        artist: Artist = RandomArtistGenerator.generate(),
        date: Date = RandomDateGenerator.generate()
    ) = PreviousArtistSearch(artist = artist, date = date)
}

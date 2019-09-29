package com.jbr.asharplibrary.random

import com.jbr.musicbrainz.model.MBSearchArtistResult
import kotlin.random.Random

object RandomMBSearchArtistResultGenerator {

    fun generate(
        count: Int = Random.nextInt(),
        artists: List<com.jbr.musicbrainz.model.MBArtist> = RandomMBArtistGenerator.generateList()
    ) = MBSearchArtistResult(count, artists)
}
package com.jbr.asharplibrary.random

import com.jbr.asharplibrary.musicbrainz.dto.MBArtist
import com.jbr.asharplibrary.musicbrainz.dto.MBSearchArtistResult
import kotlin.random.Random

object RandomMBSearchArtistResultGenerator {

    fun generate(
        count: Int = Random.nextInt(),
        artists: List<MBArtist> = RandomMBArtistGenerator.generateList()
    ) = MBSearchArtistResult(count, artists)
}
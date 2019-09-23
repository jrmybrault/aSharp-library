package com.jbr.asharplibrary.musicbrainz.dto

data class MBSearchArtistResult(val count: Int, val offset: Int, val artists: List<MBArtist>)
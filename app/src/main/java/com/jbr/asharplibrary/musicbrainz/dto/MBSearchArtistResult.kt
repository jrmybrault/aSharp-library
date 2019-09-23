package com.jbr.asharplibrary.musicbrainz.dto

data class MBSearchArtistResult(val count: UInt, val offset: UInt, val artists: List<MBArtist>)
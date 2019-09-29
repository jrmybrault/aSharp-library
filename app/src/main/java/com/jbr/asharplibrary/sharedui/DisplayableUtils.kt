package com.jbr.asharplibrary.shared.ui

import com.jbr.asharplibrary.R
import com.jbr.coredomain.searchartist.ArtistType

fun ArtistType.displayTextId(): Int {
    return when (this) {
        ArtistType.SOLO -> R.string.artist_type_solo
        ArtistType.BAND -> R.string.artist_type_band
        ArtistType.OTHER -> R.string.artist_type_other
    }
}

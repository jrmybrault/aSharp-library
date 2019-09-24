package com.jbr.asharplibrary.artistdetails.domain

import com.jbr.asharplibrary.shareddomain.ReleaseIdentifier
import java.util.*

data class Release(
    val identifier: ReleaseIdentifier,
    val title: String,
    val releaseDate: Date,
    val primaryType: PrimaryType,
    val secondaryTypes: List<SecondaryType>
) {

    enum class PrimaryType {

        ALBUM,
        SINGLE,
        EP,
        OTHER
    }

    enum class SecondaryType {

        COMPILATION,
        SOUNDTRACK,
        LIVE,
        OTHER
    }
}
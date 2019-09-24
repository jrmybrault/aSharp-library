package com.jbr.asharplibrary.musicbrainz.dto

import com.google.gson.annotations.SerializedName

data class MBReleaseGroup(val title: String, val primaryType: PrimaryType, val firstReleaseDate: String) {

    enum class PrimaryType {

        @SerializedName("Album")
        ALBUM,
        @SerializedName("Single")
        SINGLE,
        @SerializedName("Ep")
        EP,
        @SerializedName("Broadcast")
        BROADCAST,
        @SerializedName("Other")
        OTHER
    }
}

package com.jbr.coredomain.artistdetails

import androidx.lifecycle.LiveData

interface ArtistDetailsLoader {

    val artist: LiveData<DetailedArtist>

    suspend fun loadArtist(identifier: String)
}

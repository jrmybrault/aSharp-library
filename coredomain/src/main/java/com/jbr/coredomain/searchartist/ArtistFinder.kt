package com.jbr.coredomain.searchartist

import androidx.lifecycle.LiveData

interface ArtistFinder {

    val results: LiveData<List<Artist>>

    suspend fun search(text: String?)
    suspend fun loadNextPage()
}

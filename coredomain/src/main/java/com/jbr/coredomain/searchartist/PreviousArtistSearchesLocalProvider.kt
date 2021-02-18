package com.jbr.coredomain.searchartist

import androidx.lifecycle.LiveData
import com.jbr.coredomain.SearchIdentifier

interface PreviousArtistSearchesLocalProvider {

    fun getAll(): LiveData<List<PreviousArtistSearchMappable>>
    fun get(identifier: SearchIdentifier): PreviousArtistSearch?

    fun add(search: PreviousArtistSearch)
    fun remove(search: PreviousArtistSearch)
}
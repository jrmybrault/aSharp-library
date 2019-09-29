package com.jbr.asharplibrary.searchartist.domain

import androidx.lifecycle.LiveData
import com.jbr.asharplibrary.shareddomain.SearchIdentifier

interface PreviousArtistSearchesLocalProvider {

    fun getAll(): LiveData<List<PreviousArtistSearchMappable>>
    fun get(identifier: SearchIdentifier): PreviousArtistSearch?

    fun add(search: PreviousArtistSearch)
    fun remove(search: PreviousArtistSearch)
}
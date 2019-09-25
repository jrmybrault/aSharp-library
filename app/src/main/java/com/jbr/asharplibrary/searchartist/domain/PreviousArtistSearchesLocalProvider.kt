package com.jbr.asharplibrary.searchartist.domain

import androidx.lifecycle.LiveData

interface PreviousArtistSearchesLocalProvider {

    fun getAll(): LiveData<List<PreviousArtistSearchMappable>>

    fun add(search: PreviousArtistSearch)
    fun remove(search: PreviousArtistSearch)
}
package com.jbr.coredomain.searchartist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface PreviousArtistSearchesRepository {

    val searches: LiveData<List<PreviousArtistSearch>>

    suspend fun add(search: PreviousArtistSearch)
    suspend fun remove(search: PreviousArtistSearch)
}

class PreviousArtistSearchesLocalRepository(private val localProvider: PreviousArtistSearchesLocalProvider) :
    PreviousArtistSearchesRepository {

    //region - Properties

    override val searches: LiveData<List<PreviousArtistSearch>> = Transformations.map(localProvider.getAll()) { searches ->
        searches.map { it.asDomain() }
    }

    //endregion

    //region - Functions

    override suspend fun add(search: PreviousArtistSearch) {
        withContext(Dispatchers.IO) {
            localProvider.add(search)
        }
    }

    override suspend fun remove(search: PreviousArtistSearch) {
        withContext(Dispatchers.IO) {
            localProvider.remove(search)
        }
    }

    //endregion
}

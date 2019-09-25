package com.jbr.asharplibrary.searchartist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistType
import com.jbr.asharplibrary.searchartist.domain.PreviousArtistSearch
import com.jbr.asharplibrary.searchartist.domain.PreviousArtistSearchesLocalProvider
import com.jbr.utils.dateFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.random.Random

interface PreviousArtistSearchesRepository {

    val searches: LiveData<List<PreviousArtistSearch>>

    suspend fun add(search: PreviousArtistSearch)
    suspend fun remove(search: PreviousArtistSearch)
}

class PreviousArtistSearchesLocalRepository(private val localProvider: PreviousArtistSearchesLocalProvider) : PreviousArtistSearchesRepository {

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

//region - Fake repository

class FakePreviousArtistSearchesRepository : PreviousArtistSearchesRepository {

    //region - Properties

    override val searches: MutableLiveData<List<PreviousArtistSearch>> = MutableLiveData(emptyList())

    //endregion

    //region - Init

    init {
        val numberOfFakes = Random.nextInt(0, 4)
        val fakeResults = (0..numberOfFakes).map {
            PreviousArtistSearch(
                date = GregorianCalendar().dateFrom(2019, 10, 10),
                artist = Artist(
                    identifier = "artistId",
                    name = "Searched artist n° $it",
                    type = ArtistType.SOLO,
                    sortName = "Searched artist n° $it",
                    score = 100
                )
            )
        }

        searches.postValue(fakeResults)
    }

    //endregion

    //region - Functions

    override suspend fun add(search: PreviousArtistSearch) {
        withContext(Dispatchers.IO) {
            Thread.sleep(FAKE_ADD_DELAY)

            val newSearchedArtists = searches.value!!.toMutableList()
            newSearchedArtists.add(search)

            searches.postValue(newSearchedArtists)
        }
    }

    override suspend fun remove(search: PreviousArtistSearch) {
        // TODO:
    }

    //endregion

    companion object {
        private const val FAKE_ADD_DELAY = 100L // In milliseconds
    }
}

//endregion
package com.jbr.asharplibrary.searchartist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistType
import com.jbr.asharplibrary.searchartist.domain.PreviousArtistSearch
import com.jbr.utils.dateFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.random.Random

interface PreviousSearchesLoader {

    val searchedArtists: LiveData<List<PreviousArtistSearch>>

    suspend fun load()
}

class FakePreviousSearchesLoader : PreviousSearchesLoader {

    //region - Properties

    private val fakeDelay = 1500L // In milliseconds

    override val searchedArtists: MutableLiveData<List<PreviousArtistSearch>> = MutableLiveData(emptyList())

    //endregion

    //region - Functions

    override suspend fun load() {
        withContext(Dispatchers.IO) {
            Thread.sleep(fakeDelay)

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

            searchedArtists.postValue(fakeResults)
        }
    }

    //endregion
}
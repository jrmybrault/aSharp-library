package com.jbr.asharplibrary.searchartist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

interface IArtistFinder { // FIXME: Rename in ArtistFinder

    val results: LiveData<List<Artist>>

    suspend fun search(text: String?)
}

class FakeArtistFinder : IArtistFinder {

    //region - Properties

    private val fakeDelay = 1500L // In milliseconds

    override val results: MutableLiveData<List<Artist>> = MutableLiveData(emptyList())

    //endregion

    //region - Functions

    override suspend fun search(text: String?) {
        withContext(Dispatchers.IO) {
            Thread.sleep(fakeDelay)

            val numberOfFakes = Random.nextInt(0, 4)
            val fakeResults = (0..numberOfFakes).map {
                Artist(
                    identifier = "artistId",
                    name = "$text $it",
                    type = ArtistType.SOLO,
                    sortName = "$text $it",
                    score = 100
                )
            }

            results.postValue(fakeResults)
        }
    }

    //endregion
}
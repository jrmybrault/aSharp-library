package com.jbr.asharplibrary.searchartist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

interface ArtistFinder {

    val results: LiveData<List<Artist>>

    suspend fun search(text: String?)
    suspend fun loadNextPage()
}

class FakeArtistFinder : ArtistFinder {

    //region - Properties
    
    override val results: MutableLiveData<List<Artist>> = MutableLiveData(emptyList())

    //endregion

    //region - Functions

    override suspend fun search(text: String?) {
        withContext(Dispatchers.IO) {
            Thread.sleep(FAKE_DELAY)

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

    override suspend fun loadNextPage() {
        // Do nothing
    }

    //endregion

    private companion object {

        private const val FAKE_DELAY = 1500L // In milliseconds
    }
}
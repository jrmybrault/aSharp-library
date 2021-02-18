package com.jbr.asharplibrary.searchartist.infra

import androidx.lifecycle.MutableLiveData
import com.jbr.coredomain.searchartist.Artist
import com.jbr.coredomain.searchartist.ArtistFinder
import com.jbr.coredomain.searchartist.ArtistType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

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

package com.jbr.asharplibrary.searchartist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.searchartist.domain.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlin.random.nextUInt

interface IArtistFinder {

    val results: LiveData<List<Artist>>

    suspend fun search(text: String?)
}

class FakeArtistFinder : IArtistFinder {

    //region - Properties

    override val results: MutableLiveData<List<Artist>> = MutableLiveData(emptyList())

    //endregion

    //region - Functions

    override suspend fun search(text: String?) {
        withContext(Dispatchers.IO) {
            Thread.sleep(1500)

            val numberOfFakes = Random.nextUInt(4U)
            val fakeResults = (0U..numberOfFakes).map { Artist(name = "$text $it") }

            results.postValue(fakeResults)
        }
    }

    //endregion
}
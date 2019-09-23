package com.jbr.asharplibrary.searchartist.infra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.asharplibrary.musicbrainz.api.MBArtistAPI
import com.jbr.asharplibrary.musicbrainz.dto.MBArtist
import com.jbr.asharplibrary.musicbrainz.dto.asDomain
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistRemoteFinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MBArtistFinder(private val artistAPI: MBArtistAPI) : ArtistRemoteFinder {

    //region - Properties

    private val _results = MutableLiveData<List<MBArtist>>()

    override val results: LiveData<List<Artist>> = Transformations.map(_results) { mbArtists ->
        mbArtists.map { it.asDomain() } // FIXME: Do this on computation
    }

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //endregion

    //region - Funcs

    override suspend fun search(text: String?) {
        coroutineScope.launch {
            if (text.isNullOrEmpty()) {
                _results.value = emptyList()
                return@launch
            }

            val searchArtistPromise = artistAPI.searchAsync(text)

            try {
                val result = searchArtistPromise.await()

                _results.value = result.artists
            } catch (exception: Exception) {
                Timber.e(exception)
            }
        }
    }

    //endregion
}
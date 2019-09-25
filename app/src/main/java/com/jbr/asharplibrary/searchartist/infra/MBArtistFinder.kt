package com.jbr.asharplibrary.searchartist.infra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.asharplibrary.musicbrainz.api.MBArtistAPI
import com.jbr.asharplibrary.musicbrainz.dto.MBArtist
import com.jbr.asharplibrary.musicbrainz.dto.asDomain
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistRemoteFinder
import com.jbr.asharplibrary.sharedfoundation.plusAssign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MBArtistFinder(private val artistAPI: MBArtistAPI) : ArtistRemoteFinder {

    //region - Properties

    private var searchText: String? = null
        set(value) {
            field = value

            resetPagination()
        }


    private val _results = MutableLiveData<List<MBArtist>>()
    override val results: LiveData<List<Artist>> = Transformations.map(_results) { mbArtists ->
        mbArtists.map { it.asDomain() } // FIXME: Do this on computation
    }

    private var currentPage = 0
    private var hasMorePage = true

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //endregion

    //region - Functions

    override suspend fun search(text: String?) {
        searchText = text

        loadCurrentPage()
    }

    private suspend fun loadCurrentPage() {
        coroutineScope.launch {
            val currentSearch = searchText

            if (currentSearch.isNullOrEmpty()) {
                _results.value = emptyList()
                return@launch
            }

            val searchArtistPromise = artistAPI.searchAsync(currentSearch, ARTIST_BY_PAGE, ARTIST_BY_PAGE * currentPage)

            try {
                val newResults = searchArtistPromise.await()

                _results += newResults.artists
            } catch (exception: Exception) {
                Timber.e(exception)
            }
        }
    }

    override suspend fun loadNextPage() {
        if (hasMorePage) {
            currentPage++

            loadCurrentPage()
        }
    }

    private fun resetPagination() {
        _results.value = emptyList()

        currentPage = 0
        hasMorePage = true
    }

    //endregion

    companion object {

        private const val ARTIST_BY_PAGE = 15
    }
}

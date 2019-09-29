package com.jbr.asharplibrary.searchartist.infra

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.coredomain.searchartist.Artist
import com.jbr.coredomain.searchartist.ArtistRemoteFinder
import com.jbr.musicbrainz.api.MBArtistAPI
import com.jbr.musicbrainz.model.asDomain
import com.jbr.sharedfoundation.plusAssign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

internal class MBArtistFinder(private val artistAPI: MBArtistAPI) : ArtistRemoteFinder {

    //region - Properties

    private var searchText: String? = null
        set(value) {
            field = value

            resetPagination()
        }

    private val _results = MutableLiveData<List<com.jbr.musicbrainz.model.MBArtist>>()
    override val results: LiveData<List<Artist>> = Transformations.map(_results) { mbArtists ->
        mbArtists.map { it.asDomain() } // FIXME: Do this on computation
    }

    private var _currentPage = 0
    @VisibleForTesting
    val currentPage: Int
        get() = _currentPage

    private var _hasMorePages = true
    @VisibleForTesting
    val hasMorePages: Boolean
        get() = _hasMorePages

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

            val searchArtistPromise = artistAPI.searchAsync(currentSearch, ARTIST_BY_PAGE, ARTIST_BY_PAGE * _currentPage)

            try {
                val newResults = searchArtistPromise.await()

                _results += newResults.artists

                _hasMorePages = _results.value?.size ?: 0 < newResults.count
            } catch (exception: Exception) {
                Timber.e(exception) // FIXME: Propagate the error properly.
            }
        }
    }

    override suspend fun loadNextPage() {
        if (_hasMorePages) {
            _currentPage++

            loadCurrentPage()
        }
    }

    fun resetPagination() {
        _results.value = emptyList()

        _currentPage = 0
        _hasMorePages = true
    }

    //endregion

    private companion object {

        private const val ARTIST_BY_PAGE = 15
    }
}

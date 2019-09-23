package com.jbr.asharplibrary.searchartist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.searchartist.usecase.IArtistFinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchArtistViewModel(application: Application, private val finder: IArtistFinder) : AndroidViewModel(application) {

    //region - Properties

    var searchText: String? = null
        set(value) {
            field = value

            refreshSearch()
        }

    private val foundArtists = finder.results

    val displayableFoundArtists: LiveData<List<DisplayableFoundArtistItem>> = Transformations.map(foundArtists) { artists ->
        artists
            .sortedWith(ArtistSortComparator())
            .map { DisplayableFoundArtistItem(it) }
    }

    private val _isSearching = MutableLiveData(false)
    val isSearching: LiveData<Boolean>
        get() = _isSearching

    val searchResultText: LiveData<String> = Transformations.map(foundArtists) { artists ->
        if (searchText.isNullOrEmpty()) {
            ""
        } else {
            application.resources.getQuantityString(
                R.plurals.search_artist_number_of_results,
                artists.size,
                searchText ?: "",
                artists.size
            )
        }
    }


    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //endregion

    //region - Functions

    private fun refreshSearch() {
        _isSearching.value = true

        viewModelScope.launch {
            finder.search(searchText)

            _isSearching.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    //endregion
}
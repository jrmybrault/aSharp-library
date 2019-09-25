package com.jbr.asharplibrary.searchartist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.searchartist.usecase.ArtistFinder
import com.jbr.asharplibrary.searchartist.usecase.PreviousSearchesLoader
import com.jbr.asharplibrary.searchartist.usecase.SearchArtistNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class SearchArtistViewModel(
    application: Application,
    private val finder: ArtistFinder,
    private val previousSearchesLoader: PreviousSearchesLoader
) : AndroidViewModel(application) {

    //region - Properties

    //region - Search 

    var searchText: String? = null
        set(value) {
            field = value

            _shouldDisplayPreviousSearches.value = value.isNullOrEmpty()
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

    //endregion

    //region - Previous Search

    private val _shouldDisplayPreviousSearches = MutableLiveData<Boolean>()
    val shouldDisplayPreviousSearches: LiveData<Boolean>
        get() = _shouldDisplayPreviousSearches

    private val previousSearchesArtists = previousSearchesLoader.searchedArtists

    val displayableSearchedArtists: LiveData<List<DisplayableFoundArtistItem>> = Transformations.map(previousSearchesArtists) { artists ->
        artists.map { DisplayableFoundArtistItem(it.artist) }
    }

    //endregion

    var navigator: WeakReference<SearchArtistNavigator>? = null

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //endregion

    //region - Init

    init {
        loadPreviousSearches()
    }

    //region - Functions

    private fun loadPreviousSearches() {
        viewModelScope.launch {
            previousSearchesLoader.load()
        }
    }

    private fun refreshSearch() {
        _isSearching.value = true

        viewModelScope.launch {
            finder.search(searchText)

            _isSearching.value = false
        }
    }

    fun handleSelectionOfArtist(index: Int) {
        val selectedArtist = displayableFoundArtists.value!![index]

        navigator?.get()?.goToArtistDetails(selectedArtist.identifier)
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    //endregion
}
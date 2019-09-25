package com.jbr.asharplibrary.searchartist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.searchartist.domain.PreviousArtistSearch
import com.jbr.asharplibrary.searchartist.usecase.ArtistFinder
import com.jbr.asharplibrary.searchartist.usecase.PreviousArtistSearchesRepository
import com.jbr.asharplibrary.searchartist.usecase.SearchArtistNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class SearchArtistViewModel(
    application: Application,
    private val finder: ArtistFinder,
    private val previousArtistSearchesRepository: PreviousArtistSearchesRepository
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

    private val previousSearchesArtists = previousArtistSearchesRepository.searches

    val displayableSearchedArtists: LiveData<List<DisplayableFoundArtistItem>> = Transformations.map(previousSearchesArtists) { searches ->
        searches
            .sortedByDescending { it.date }
            .map { DisplayableFoundArtistItem(it.artist) }
    }

    //endregion

    var navigator: WeakReference<SearchArtistNavigator>? = null

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

    fun handleSelectionOfArtist(index: Int) {
        val selectedArtist = foundArtists.value!![index]

        viewModelScope.launch {
            previousArtistSearchesRepository.add(PreviousArtistSearch(selectedArtist))
        }

        navigator?.get()?.goToArtistDetails(selectedArtist.identifier)
    }

    fun handleSelectionOfPreviousSearch(index: Int) {
        val selectedArtist = previousSearchesArtists.value!![index].artist

        navigator?.get()?.goToArtistDetails(selectedArtist.identifier)
    }

    fun handleTapOnClearPreviousSearch(index: Int) {
        val selectedSearch = previousSearchesArtists.value!![index]

        viewModelScope.launch {
            previousArtistSearchesRepository.remove(selectedSearch)
        }
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    //endregion
}
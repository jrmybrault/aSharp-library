package com.jbr.asharplibrary.artistdetails.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.asharplibrary.artistdetails.ui.about.DisplayableArtistAbout
import com.jbr.asharplibrary.artistdetails.ui.discography.DisplayableArtistReleaseItemType
import com.jbr.asharplibrary.artistdetails.ui.discography.DisplayableReleaseCategoryItem
import com.jbr.asharplibrary.artistdetails.ui.discography.DisplayableReleaseInfoItem
import com.jbr.asharplibrary.artistdetails.ui.discography.ReleasePrimaryTypeComparator
import com.jbr.asharplibrary.artistdetails.usecase.ArtistDetailsLoader
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArtistDetailsViewModel(application: Application, private val loader: ArtistDetailsLoader) : AndroidViewModel(application) {

    //region - Properties

    private val loadedArtist = loader.artist

    val displayableArtistName: LiveData<String> = Transformations.map(loadedArtist) { it.name }

    val displayableArtistAbout: LiveData<DisplayableArtistAbout> = Transformations.map(loadedArtist) {
        DisplayableArtistAbout(it, application.resources)
    }

    val displayableArtistReleases: LiveData<List<DisplayableArtistReleaseItemType>> = Transformations.map(loadedArtist) { artist ->
        val results = mutableListOf<DisplayableArtistReleaseItemType>()

        val groupedReleases = artist.releases
            .sortedByDescending { it.releaseDate }
            .groupBy { it.primaryType }
            .toSortedMap(ReleasePrimaryTypeComparator())

        groupedReleases.forEach { entry ->
            results.add(DisplayableReleaseCategoryItem(entry.key))
            results.addAll(entry.value.map { DisplayableReleaseInfoItem(it, application.resources) })
        }

        results
    }

    val randomReleaseCoverUri: LiveData<Uri?> = Transformations.map(loadedArtist) { artist ->
        if (artist.releases.isEmpty()) {
            null
        } else {
            val randomRelease = artist.releases.random()
            DisplayableReleaseInfoItem(randomRelease, application.resources).largeFrontCoverUri
        }
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //endregion

    //region - Functions

    fun loadArtist(identifier: ArtistIdentifier) {
        _isLoading.value = true

        viewModelScope.launch {
            loader.loadArtist(identifier)

            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    //endregion
}

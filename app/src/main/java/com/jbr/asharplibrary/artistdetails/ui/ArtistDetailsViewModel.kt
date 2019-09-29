package com.jbr.asharplibrary.artistdetails.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.asharplibrary.artistdetails.navigation.ArtistDetailsNavigator
import com.jbr.asharplibrary.artistdetails.ui.about.DisplayableArtistAbout
import com.jbr.asharplibrary.artistdetails.ui.discography.DisplayableArtistReleaseItemType
import com.jbr.asharplibrary.artistdetails.ui.discography.DisplayableReleaseCategoryItem
import com.jbr.asharplibrary.artistdetails.ui.discography.DisplayableReleaseInfoItem
import com.jbr.asharplibrary.artistdetails.ui.discography.ReleasePrimaryTypeComparator
import com.jbr.coredomain.ArtistIdentifier
import com.jbr.coredomain.artistdetails.ArtistDetailsLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

internal class ArtistDetailsViewModel(application: Application, private val loader: ArtistDetailsLoader) : AndroidViewModel(application) {

    //region - Properties

    private val loadedArtist = loader.artist

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val displayableArtistName: LiveData<String> = Transformations.map(loadedArtist) { it.name }

    val displayableArtistAbout: LiveData<DisplayableArtistAbout> = Transformations.map(loadedArtist) {
        DisplayableArtistAbout(it, application.resources)
    }

    val displayableArtistReleases: LiveData<List<DisplayableArtistReleaseItemType>> = Transformations.map(loadedArtist) { artist ->
        val items = mutableListOf<DisplayableArtistReleaseItemType>()

        val groupedReleases = artist.releases
            .sortedByDescending { it.releaseDate }
            .groupBy { it.primaryType }
            .toSortedMap(ReleasePrimaryTypeComparator())

        groupedReleases.forEach { entry ->
            items.add(DisplayableReleaseCategoryItem(entry.key))
            items.addAll(entry.value.map { DisplayableReleaseInfoItem(it, application.resources) })
        }

        items
    }

    val randomReleaseCoverUri: LiveData<Uri?> = Transformations.map(loadedArtist) { artist ->
        if (artist.releases.isEmpty()) {
            null
        } else {
            val randomRelease = artist.releases.random()
            DisplayableReleaseInfoItem(randomRelease, application.resources).largeFrontCoverUri
        }
    }

    var navigator: WeakReference<ArtistDetailsNavigator>? = null

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

    fun handleTapOnMoreInfo() {
        launchGoggleQuery()
    }

    private fun launchGoggleQuery() {
        val artistName = loadedArtist.value!!.name
        navigator?.get()?.openArtistWebSearch(artistName)
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    //endregion
}

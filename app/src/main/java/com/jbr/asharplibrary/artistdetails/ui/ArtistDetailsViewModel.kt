package com.jbr.asharplibrary.artistdetails.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jbr.asharplibrary.artistdetails.ui.about.DisplayableArtistAbout
import com.jbr.asharplibrary.artistdetails.ui.discography.DisplayableArtistReleaseItem
import com.jbr.asharplibrary.artistdetails.usecase.ArtistDetailsLoader
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArtistDetailsViewModel(application: Application, private val loader: ArtistDetailsLoader) : AndroidViewModel(application) {

    //region - Properties

    private val loadedArtist = loader.artist

    val displayableArtistAbout: LiveData<DisplayableArtistAbout> = Transformations.map(loadedArtist) {
        DisplayableArtistAbout(it, application.resources)
    }

    val displayableArtistReleases: LiveData<List<DisplayableArtistReleaseItem>> = Transformations.map(loadedArtist) { artist ->
        artist.releases.map { DisplayableArtistReleaseItem(it, application.resources) }
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

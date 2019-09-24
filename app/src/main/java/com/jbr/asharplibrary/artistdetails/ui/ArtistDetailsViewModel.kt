package com.jbr.asharplibrary.artistdetails.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.artistdetails.usecase.ArtistDetailsLoader
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArtistDetailsViewModel(application: Application, private val loader: ArtistDetailsLoader) : AndroidViewModel(application) {

    //region - Properties

    private val loadedArtist = loader.artist
    
    private val _isLoading = MutableLiveData<Boolean>(false)
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

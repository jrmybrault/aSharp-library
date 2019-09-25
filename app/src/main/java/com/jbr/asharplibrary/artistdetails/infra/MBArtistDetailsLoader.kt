package com.jbr.asharplibrary.artistdetails.infra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.artistdetails.domain.ArtistDetailsRemoteLoader
import com.jbr.asharplibrary.artistdetails.domain.DetailedArtist
import com.jbr.asharplibrary.musicbrainz.api.MBArtistAPI
import com.jbr.asharplibrary.musicbrainz.dto.asDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MBArtistDetailsLoader(private val artistAPI: MBArtistAPI) : ArtistDetailsRemoteLoader {

    //region - Properties

    private val _artist = MutableLiveData<DetailedArtist>()

    override val artist: LiveData<DetailedArtist>
        get() = _artist

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //endregion

    //region - Functions

    override suspend fun loadArtist(identifier: String) {
        coroutineScope.launch {
            val loadArtistDetailsPromise = artistAPI.getDetailsAsync(identifier)
            val loadArtistWikipediaExtractPromise = artistAPI.getWikipediaExtractAsync(identifier)

            try {
                val artistDetails = loadArtistDetailsPromise.await()
                val artistWikipediaExtractContainer = loadArtistWikipediaExtractPromise.await()

                val detailedArtist = artistDetails.asDomain()
                detailedArtist.wikipediaExtract = artistWikipediaExtractContainer.wikipediaExtract.content

                _artist.postValue(detailedArtist)
            } catch (exception: Exception) {
                Timber.e(exception)
            }
        }
    }

    //endregion
}
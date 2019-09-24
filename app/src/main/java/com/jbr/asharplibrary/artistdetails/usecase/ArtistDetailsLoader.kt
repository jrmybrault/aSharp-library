package com.jbr.asharplibrary.artistdetails.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.artistdetails.domain.DetailedArtist
import com.jbr.asharplibrary.searchartist.domain.ArtistType
import com.jbr.utils.dateFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

interface ArtistDetailsLoader {

    val artist: LiveData<DetailedArtist>

    suspend fun loadArtist(identifier: String)
}

class FakeArtistDetailsLoader : ArtistDetailsLoader {

    //region - Properties

    private val fakeDelay = 1500L // In milliseconds

    override val artist = MutableLiveData<DetailedArtist>()

    //endregion

    //region - Functions

    override suspend fun loadArtist(identifier: String) {
        withContext(Dispatchers.IO) {
            Thread.sleep(fakeDelay)

            val fakeArtist = DetailedArtist(
                identifier = "artistId",
                type = ArtistType.SOLO,
                name = "Ennio Morricone",
                gender = DetailedArtist.Gender.MALE,
                ipiCodes = listOf("00021552128"),
                isniCodes = listOf("0000000121225602", "0000000368638409"),
                countryName = "Italy",
                beginningArea = "Rome",
                lifeSpanBeginning = GregorianCalendar().dateFrom(1928, 10, 11),
                lifeSpanEnd = null,
                typePrecision = "Composer"
            )

            artist.postValue(fakeArtist)
        }
    }

    //endregion
}
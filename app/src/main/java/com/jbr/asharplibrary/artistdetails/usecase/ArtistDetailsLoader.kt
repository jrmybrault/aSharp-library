package com.jbr.asharplibrary.artistdetails.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.artistdetails.domain.DetailedArtist
import com.jbr.asharplibrary.artistdetails.domain.Gender
import com.jbr.utils.date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

interface ArtistDetailsLoader {

    val artist: LiveData<DetailedArtist?>

    suspend fun loadArtist(identifier: String)
}

class FakeArtistDetailsLoader : ArtistDetailsLoader {

    //region - Properties

    private val fakeDelay = 1500L // In milliseconds

    override val artist: MutableLiveData<DetailedArtist?> = MutableLiveData(null)

    //endregion

    //region - Functions

    override suspend fun loadArtist(identifier: String) {
        withContext(Dispatchers.IO) {
            Thread.sleep(fakeDelay)

            val fakeArtist = DetailedArtist(
                identifier = "artistId",
                name = "Ennio Morricone",
                countryName = "Italy",
                areaBegin = "Rome",
                lifeSpanBegin = GregorianCalendar().date(1928, 10, 11),
                lifeSpanEnd = null,
                typeDetail = "Composer",
                gender = Gender.MALE
            )

            artist.postValue(fakeArtist)
        }
    }

    //endregion
}
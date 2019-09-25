package com.jbr.asharplibrary.artistdetails.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.artistdetails.domain.DetailedArtist
import com.jbr.asharplibrary.artistdetails.domain.Release
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

    private val fakeDelay = 1500L // In milliseconds // FIXME: Move this into companion

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
                typePrecision = "Composer",
                releases = listOf(
                    Release(
                        "ep1",
                        title = "First EP",
                        releaseDate = GregorianCalendar().dateFrom(1989, 10, 10),
                        primaryType = Release.PrimaryType.EP,
                        secondaryTypes = emptyList()
                    ),
                    Release(
                        "single1",
                        title = "First single",
                        releaseDate = GregorianCalendar().dateFrom(1990, 10, 10),
                        primaryType = Release.PrimaryType.SINGLE,
                        secondaryTypes = emptyList()
                    ),
                    Release(
                        "single2",
                        title = "Second single",
                        releaseDate = GregorianCalendar().dateFrom(1990, 10, 10),
                        primaryType = Release.PrimaryType.SINGLE,
                        secondaryTypes = emptyList()
                    ),
                    Release(
                        "album1",
                        title = "First album",
                        releaseDate = GregorianCalendar().dateFrom(1990, 10, 10),
                        primaryType = Release.PrimaryType.ALBUM,
                        secondaryTypes = emptyList()
                    ),
                    Release(
                        "album2",
                        title = "Second album",
                        releaseDate = GregorianCalendar().dateFrom(1992, 10, 10),
                        primaryType = Release.PrimaryType.ALBUM,
                        secondaryTypes = emptyList()
                    ),
                    Release(
                        "single3",
                        title = "Third single",
                        releaseDate = GregorianCalendar().dateFrom(1995, 10, 10),
                        primaryType = Release.PrimaryType.SINGLE,
                        secondaryTypes = emptyList()
                    ),
                    Release(
                        "releaseId5",
                        title = "Third album",
                        releaseDate = GregorianCalendar().dateFrom(1995, 10, 10),
                        primaryType = Release.PrimaryType.ALBUM,
                        secondaryTypes = emptyList()
                    ),
                    Release(
                        "compilation1",
                        title = "First compilation",
                        releaseDate = GregorianCalendar().dateFrom(1995, 11, 10),
                        primaryType = Release.PrimaryType.ALBUM,
                        secondaryTypes = listOf(Release.SecondaryType.COMPILATION)
                    )
                )
            )

            artist.postValue(fakeArtist)
        }
    }

    //endregion
}
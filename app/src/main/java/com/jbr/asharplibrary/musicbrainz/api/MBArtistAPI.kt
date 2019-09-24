package com.jbr.asharplibrary.musicbrainz.api

import com.jbr.asharplibrary.musicbrainz.dto.MBDetailedArtist
import com.jbr.asharplibrary.musicbrainz.dto.MBSearchArtistResult
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MBArtistAPI {

    @GET("artist")
    fun searchAsync(@Query("query") searchText: String): Deferred<MBSearchArtistResult>

    @GET("artist/{artistId}?inc=recordings+releases+release-groups+works")
    fun getAsync(@Path("artistId") artistId: ArtistIdentifier): Deferred<MBDetailedArtist>
}
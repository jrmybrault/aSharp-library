package com.jbr.asharplibrary.musicbrainz.api

import com.jbr.asharplibrary.musicbrainz.dto.MBSearchArtistResult
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MBArtistAPI {

    @GET("artist")
    fun searchAsync(@Query("query") searchText: String): Deferred<MBSearchArtistResult>
}
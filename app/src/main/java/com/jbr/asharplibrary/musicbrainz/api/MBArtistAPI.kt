package com.jbr.asharplibrary.musicbrainz.api

import com.jbr.asharplibrary.musicbrainz.dto.MBDetailedArtist
import com.jbr.asharplibrary.musicbrainz.dto.MBSearchArtistResult
import com.jbr.asharplibrary.musicbrainz.dto.MBWikipediaExtract
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MBArtistAPI {

    @GET("artist")
    fun searchAsync(
        @Query("query") searchText: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<MBSearchArtistResult>

    @GET("artist/{artistId}?inc=releases+release-groups")
    fun getDetailsAsync(@Path("artistId") artistId: ArtistIdentifier): Deferred<MBDetailedArtist>

    @GET("artist/{artistId}/wikipedia-extract")
    fun getWikipediaExtractAsync(@Path("artistId") artistId: ArtistIdentifier): Deferred<MBWikipediaExtract>
}
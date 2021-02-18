package com.jbr.musicbrainz.api

import com.jbr.coredomain.ArtistIdentifier
import com.jbr.musicbrainz.model.MBDetailedArtist
import com.jbr.musicbrainz.model.MBSearchArtistResult
import com.jbr.musicbrainz.model.MBWikipediaExtractContainer
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MBArtistAPI {

    @GET("ws/2/artist")
    fun searchAsync(
        @Query("query") searchText: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<MBSearchArtistResult>

    @GET("ws/2/artist/{artistId}?inc=releases+release-groups+ratings")
    fun getDetailsAsync(@Path("artistId") artistId: ArtistIdentifier): Deferred<MBDetailedArtist>

    @GET("artist/{artistId}/wikipedia-extract")
    fun getWikipediaExtractAsync(@Path("artistId") artistId: ArtistIdentifier): Deferred<MBWikipediaExtractContainer>
}
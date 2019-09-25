package com.jbr.asharplibrary.musicbrainz.api

import com.jbr.asharplibrary.musicbrainz.dto.MBDetailedArtist
import com.jbr.asharplibrary.musicbrainz.dto.MBSearchArtistResult
import com.jbr.asharplibrary.musicbrainz.dto.MBWikipediaExtractContainer
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MBArtistAPI {

    @GET("ws/2/artist")
    fun searchAsync(@Query("query") searchText: String): Deferred<MBSearchArtistResult>

    @GET("ws/2/artist/{artistId}?inc=releases+release-groups+ratings")
    fun getDetailsAsync(@Path("artistId") artistId: ArtistIdentifier): Deferred<MBDetailedArtist>

    @GET("artist/{artistId}/wikipedia-extract")
    fun getWikipediaExtractAsync(@Path("artistId") artistId: ArtistIdentifier): Deferred<MBWikipediaExtractContainer>
}
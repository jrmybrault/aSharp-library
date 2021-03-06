package com.jbr.asharplibrary.searchartist.infra

import com.jbr.coredomain.SearchIdentifier
import com.jbr.coredomain.searchartist.PreviousArtistSearch
import com.jbr.coredomain.searchartist.PreviousArtistSearchMappable
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import java.util.*

open class RealmPreviousArtistSearch : RealmObject(), PreviousArtistSearchMappable {

    //region - Properties

    @PrimaryKey
    private var identifier: SearchIdentifier = ""

    private lateinit var artist: RealmArtist

    @Index
    private var date: Date = Date()

    //endregion

    //region - Functions

    override fun asDomain(): PreviousArtistSearch {
        return PreviousArtistSearch(
            date = date,
            artist = artist.asDomain()
        )
    }

    //endregion

    companion object {

        const val PRIMARY_KEY_NAME = "identifier"

        fun from(previousArtistSearch: PreviousArtistSearch): RealmPreviousArtistSearch {
            val realmPreviousArtistSearch = RealmPreviousArtistSearch()

            realmPreviousArtistSearch.identifier = previousArtistSearch.artist.identifier
            realmPreviousArtistSearch.date = previousArtistSearch.date
            realmPreviousArtistSearch.artist = RealmArtist.from(previousArtistSearch.artist)

            return realmPreviousArtistSearch
        }
    }
}

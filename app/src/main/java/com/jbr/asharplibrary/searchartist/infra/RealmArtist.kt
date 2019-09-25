package com.jbr.asharplibrary.searchartist.infra

import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistType
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmArtist : RealmObject() {

    //region - Properties

    @PrimaryKey
    var identifier: ArtistIdentifier = ""

    private var name: String = ""
    private var sortName: String = ""

    private var type: String = ""

    private var score: Int = 0

    //endregion

    //region - Functions

    fun asDomain(): Artist {
        return Artist(
            identifier = identifier,
            name = name,
            type = ArtistType.valueOf(type),
            sortName = sortName,
            score = score
        )
    }

    //endregion

    companion object {

        fun from(artist: Artist): RealmArtist {
            val realmArtist = RealmArtist()

            realmArtist.identifier = artist.identifier
            realmArtist.name = artist.name
            realmArtist.sortName = artist.sortName
            realmArtist.type = artist.type.toString()
            realmArtist.score = artist.score

            return realmArtist
        }
    }
}

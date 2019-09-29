package com.jbr.asharplibrary.searchartist.infra

import androidx.lifecycle.LiveData
import com.jbr.asharplibrary.searchartist.infra.RealmPreviousArtistSearch.Companion.PRIMARY_KEY_NAME
import com.jbr.asharplibrary.sharedinfra.asLiveData
import com.jbr.coredomain.SearchIdentifier
import com.jbr.coredomain.searchartist.PreviousArtistSearch
import com.jbr.coredomain.searchartist.PreviousArtistSearchMappable
import com.jbr.coredomain.searchartist.PreviousArtistSearchesLocalProvider
import io.realm.Realm

class RealmPreviousArtistSearchesDao : PreviousArtistSearchesLocalProvider {

    //region - Functions

    @SuppressWarnings("UNCHECKED_CAST")
    override fun getAll(): LiveData<List<PreviousArtistSearchMappable>> {
        val realm = Realm.getDefaultInstance()
        realm.refresh()

        val previousSearches = realm.where(RealmPreviousArtistSearch::class.java)
            .findAllAsync()

        return previousSearches.asLiveData() as LiveData<List<PreviousArtistSearchMappable>>
    }

    override fun get(identifier: SearchIdentifier): PreviousArtistSearch? {
        val realm = Realm.getDefaultInstance()

        return realm.where(RealmPreviousArtistSearch::class.java)
            .equalTo(PRIMARY_KEY_NAME, identifier)
            .findFirst()
            ?.asDomain()
    }

    override fun add(search: PreviousArtistSearch) {
        val realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        realm.insertOrUpdate(RealmPreviousArtistSearch.from(search))
        realm.commitTransaction()
    }

    override fun remove(search: PreviousArtistSearch) {
        val realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        get(search.identifier, realm)?.deleteFromRealm()
        realm.commitTransaction()
    }

    private fun get(identifier: SearchIdentifier, realm: Realm): RealmPreviousArtistSearch? {
        return realm.where(RealmPreviousArtistSearch::class.java)
            .equalTo(PRIMARY_KEY_NAME, identifier)
            .findFirst()
    }

    //endregion
}

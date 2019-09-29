package com.jbr.asharplibrary.sharedinfra

import androidx.lifecycle.LiveData
import com.jbr.sharedfoundation.CloseableLiveData
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults

class RealmResultsLiveData<T : RealmModel>(
    private val realmResults: RealmResults<T>,
    private val realm: Realm = realmResults.realm
) : LiveData<List<T>>(), CloseableLiveData {

    private val listener = RealmChangeListener<RealmResults<T>> { results -> value = results }

    override fun onActive() {
        value = realmResults
        realmResults.addChangeListener(listener)
    }

    override fun onInactive() {
        realmResults.removeChangeListener(listener)
    }

    override fun close() {
        realm.close()
    }
}

fun <T : RealmModel> RealmResults<T>.asLiveData() = RealmResultsLiveData(this)

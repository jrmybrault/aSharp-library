package com.jbr.asharplibrary.searchartist.infra

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.jbr.asharplibrary.random.RandomPreviousArtistSearchGenerator
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RealmPreviousArtistSearchesDaoTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var dao: RealmPreviousArtistSearchesDao

    //region - Setup & Teardown

    @Before
    fun setUp() {
        Realm.init(context)

        val testConfig = RealmConfiguration.Builder().inMemory().name("test-realm").build()
        Realm.setDefaultConfiguration(testConfig)

        dao = RealmPreviousArtistSearchesDao()
    }

    //endregion

    @Test
    fun addingAPreviousSearch() {
        // Arrange
        val fakePreviousSearch = RandomPreviousArtistSearchGenerator.generate()

        // Act
        dao.add(fakePreviousSearch)

        // Assert
        val savedSearch = dao.get(fakePreviousSearch.identifier)

        assertNotNull(savedSearch)
    }

    @Test
    fun removingAPreviousSearch() {
        // Arrange
        val fakePreviousSearch = RandomPreviousArtistSearchGenerator.generate()

        dao.add(fakePreviousSearch)

        // Act
        dao.remove(fakePreviousSearch)

        // Assert
        val savedSearch = dao.get(fakePreviousSearch.identifier)

        assertNull(savedSearch)
    }

    // TODO: getAll() relies on findAllAsync which require a thread with a looper. Find a way to do this in order to test the function.
    /*@Test
    fun getAll() {

    }*/
}
package com.jbr.asharplibrary.searchartist.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jbr.asharplibrary.random.RandomArtistGenerator
import com.jbr.asharplibrary.random.RandomPreviousArtistSearchGenerator
import com.jbr.asharplibrary.random.RandomStringGenerator
import com.jbr.asharplibrary.random.createMockResourcesString
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.PreviousArtistSearch
import com.jbr.asharplibrary.searchartist.usecase.ArtistFinder
import com.jbr.asharplibrary.searchartist.usecase.PreviousArtistSearchesRepository
import com.jbr.asharplibrary.searchartist.usecase.SearchArtistNavigator
import com.jbr.utils.dateFrom
import com.jraska.livedata.test
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.ref.WeakReference
import java.util.*
import kotlin.random.Random

class SearchArtistViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("Main thread")

    private val mockApplication = mockk<Application>(relaxed = true)

    private val fakeResults = MutableLiveData<List<Artist>>(emptyList())
    private val mockArtistFinder = mockk<ArtistFinder>(relaxed = true) {
        every { results } returns fakeResults
    }

    private val fakePreviousSearches = MutableLiveData<List<PreviousArtistSearch>>(emptyList())
    private val mockPreviousSearchesRepository = mockk<PreviousArtistSearchesRepository>(relaxed = true) {
        every { searches } returns fakePreviousSearches
    }

    private val mockNavigator = mockk<SearchArtistNavigator>(relaxed = true)

    private val viewModel = SearchArtistViewModel(mockApplication, mockArtistFinder, mockPreviousSearchesRepository)
        .apply { navigator = WeakReference(mockNavigator) }

    //region - Setup & Teardown

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        fakeResults.postValue(emptyList())
        fakePreviousSearches.postValue(emptyList())

        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    //endregion

    @Test
    fun `show previous searches only when search text is set to null`() {
        // Arrange
        viewModel.searchText = RandomStringGenerator.generate()

        // Act
        viewModel.searchText = null

        // Assert
        viewModel.shouldDisplayPreviousSearches
            .test()
            .assertValueHistory(true)
    }

    @Test
    fun `show previous searches when search text is set to empty`() {
        // Arrange
        viewModel.searchText = RandomStringGenerator.generate()

        // Act
        viewModel.searchText = ""

        // Assert
        viewModel.shouldDisplayPreviousSearches
            .test()
            .assertValueHistory(true)
    }

    @Test
    fun `hide previous searches when a non empty search text is set`() {
        // Arrange
        viewModel.searchText = ""

        // Act
        viewModel.searchText = RandomStringGenerator.generate()

        // Assert
        viewModel.shouldDisplayPreviousSearches
            .test()
            .assertValueHistory(false)
    }

    @Test
    fun `refresh search when a search text change`() {
        // Arrange
        viewModel.searchText = ""

        val newSearchText = RandomStringGenerator.generate()

        // Act
        viewModel.searchText = newSearchText

        // Assert
        coVerify { mockArtistFinder.search(newSearchText) }
    }

    @Test
    fun `show searching while searching`() {
        // Arrange
        val observer = viewModel.isSearching
            .test()

        // Act
        viewModel.searchText = RandomStringGenerator.generate()

        // Assert
        observer.assertValueHistory(false, true) // TODO: Find a way to test when searching is reset to false
    }

    @Test
    fun `updating displayable results when results change`() {
        // Arrange
        val results = (0..10).map { RandomArtistGenerator.generate() }

        // Act
        fakeResults.value = results

        // Assert
        viewModel.displayableResults
            .test()
            .assertHasValue()
            .assertHistorySize(1)

        assertEquals(results.size, viewModel.displayableResults.value!!.size)
        assertEquals(results.map { it.identifier }.sorted(), viewModel.displayableResults.value!!.map { it.identifier }.sorted())
    }

    @Test
    fun `sorting displayable results`() {
        // Arrange
        val results = listOf(
            RandomArtistGenerator.generate(score = 80),
            RandomArtistGenerator.generate(score = 90),
            RandomArtistGenerator.generate(score = 85)
        )

        // Act
        fakeResults.value = results

        // Assert
        viewModel.displayableResults
            .test()

        assertNotEquals(results.map { it.identifier }, viewModel.displayableResults.value!!.map { it.identifier })
    }

    @Test
    fun `search result text is empty if search text is empty`() {
        // Arrange
        viewModel.searchText = RandomStringGenerator.generate()

        val observer = viewModel.searchResultText
            .test()

        // Act
        viewModel.searchText = ""

        // Assert
        observer.assertValueHistory("")
    }

    @Test
    fun `search result text is not empty when search text is not empty and results change`() {
        // Arrange
        viewModel.searchText = RandomStringGenerator.generate()

        val fakeResultText = RandomStringGenerator.generate()

        val mockResources = createMockResourcesString(fakeResultText)
        every { mockApplication.resources } returns mockResources

        val results = (0..10).map { RandomArtistGenerator.generate() }

        // Act
        fakeResults.value = results

        // Assert
        viewModel.searchResultText
            .test()

        assertEquals(fakeResultText, viewModel.searchResultText.value!!)
    }

    @Test
    fun `updating displayable previous searches when previous searches change`() {
        // Arrange
        val previousSearches = (0..10).map { RandomPreviousArtistSearchGenerator.generate() }

        // Act
        fakePreviousSearches.value = previousSearches

        // Assert
        viewModel.displayablePreviousSearches
            .test()
            .assertHasValue()
            .assertHistorySize(1)

        assertEquals(previousSearches.size, viewModel.displayablePreviousSearches.value!!.size)
        assertEquals(previousSearches.map { it.identifier }.sorted(), viewModel.displayablePreviousSearches.value!!.map { it.identifier }.sorted())
    }

    @Test
    fun `sorting displayable previous searches`() {
        // Arrange
        val previousSearches = listOf(
            RandomPreviousArtistSearchGenerator.generate(date = GregorianCalendar().dateFrom(2019, 1, 1)),
            RandomPreviousArtistSearchGenerator.generate(date = GregorianCalendar().dateFrom(2019, 11, 1)),
            RandomPreviousArtistSearchGenerator.generate(date = GregorianCalendar().dateFrom(2019, 6, 1))
        )

        // Act
        fakePreviousSearches.value = previousSearches

        // Assert
        viewModel.displayablePreviousSearches
            .test()

        assertNotEquals(previousSearches.map { it.identifier }, viewModel.displayablePreviousSearches.value!!.map { it.identifier })
    }

    @Test
    fun `handle selection of artist delegates to navigator`() {
        // Arrange
        val results = listOf(
            RandomArtistGenerator.generate(score = 90),
            RandomArtistGenerator.generate(score = 85),
            RandomArtistGenerator.generate(score = 80)
        )

        fakeResults.value = results

        viewModel.displayableResults // FIXME: This is not a clean way to wait for the displayables to be ready.
            .test()

        val fakeSelectionIndex = Random.nextInt(0, results.size - 1)

        // Act
        viewModel.handleSelectionOfArtist(fakeSelectionIndex)

        // Assert
        verify { mockNavigator.goToArtistDetails(results[fakeSelectionIndex].identifier) }
    }

    @Test
    fun `load next page when reaching list end`() {
        // Act
        viewModel.handleReachingListEnd()

        // Assert
        coVerify { mockArtistFinder.loadNextPage() }
    }

    @Test
    fun `handle selection of previous searches delegates to navigator`() {
        // Arrange
        val previousSearches = listOf(
            RandomPreviousArtistSearchGenerator.generate(date = GregorianCalendar().dateFrom(2019, 1, 1)),
            RandomPreviousArtistSearchGenerator.generate(date = GregorianCalendar().dateFrom(2019, 6, 1)),
            RandomPreviousArtistSearchGenerator.generate(date = GregorianCalendar().dateFrom(2019, 11, 1))
        )

        fakePreviousSearches.value = previousSearches

        viewModel.displayablePreviousSearches // FIXME: This is not a clean way to wait for the displayables to be ready.
            .test()

        val fakeSelectionIndex = Random.nextInt(0, previousSearches.size - 1)

        // Act
        viewModel.handleSelectionOfPreviousSearch(fakeSelectionIndex)

        // Assert
        verify { mockNavigator.goToArtistDetails(previousSearches[fakeSelectionIndex].identifier) }
    }

    @Test
    fun `handle tap on clear previous search delegates to repository`() {
        // Arrange
        val previousSearches = listOf(
            RandomPreviousArtistSearchGenerator.generate(date = GregorianCalendar().dateFrom(2019, 11, 1)),
            RandomPreviousArtistSearchGenerator.generate(date = GregorianCalendar().dateFrom(2019, 6, 1)),
            RandomPreviousArtistSearchGenerator.generate(date = GregorianCalendar().dateFrom(2019, 1, 1))
        )

        fakePreviousSearches.value = previousSearches

        viewModel.displayablePreviousSearches // FIXME: This is not a clean way to wait for the displayables to be ready.
            .test()

        val fakeSelectionIndex = Random.nextInt(0, previousSearches.size - 1)

        // Act
        viewModel.handleTapOnClearPreviousSearch(fakeSelectionIndex)

        // Assert
        coVerify { mockPreviousSearchesRepository.remove(any()) }
        // TODO: Find a way to check that the removed seach is the right one. ArgumentCaptor doesn't seem to play nice with coVerify.
    }
}
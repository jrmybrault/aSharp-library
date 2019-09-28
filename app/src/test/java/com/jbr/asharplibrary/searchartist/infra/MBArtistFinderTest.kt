package com.jbr.asharplibrary.searchartist.infra

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jbr.asharplibrary.musicbrainz.api.MBArtistAPI
import io.mockk.clearMocks
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

class MBArtistFinderTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("Main thread")

    private val mockArtistAPI = mockk<MBArtistAPI>(relaxed = true)

    private val finder = MBArtistFinder(mockArtistAPI)

    //region - Setup & Teardown

    @Before
    fun setUp() {
        finder.resetPagination()

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        clearMocks(mockArtistAPI)

        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

//endregion

/*@Test
fun `do not load if search text is empty`() = runBlockingTest {
    // Act
    finder.search("")

    // Assert
    verify(exactly = 0) { mockArtistAPI.searchAsync(any(), any(), any()) }
}

@Test
fun `loading page delegates to artist API`() = runBlockingTest {
    // Arrange
    val fakeSearchText = RandomStringGenerator.generate()

    // Act
    finder.search(fakeSearchText)

    // Assert
    verify(exactly = 1) { mockArtistAPI.searchAsync(fakeSearchText, any(), 0) }
}

@Test
fun `update results after loading`() = runBlockingTest {
    // Arrange
    val fakeArtists = (0..1).map { RandomMBArtistGenerator.generate() }
    val fakeResult = RandomMBSearchArtistResultGenerator.generate(count = Int.MAX_VALUE, artists = fakeArtists)

    every { mockArtistAPI.searchAsync(any(), any(), any()) } returns fakeResult.toDeferred()

    // Act
    finder.search(RandomStringGenerator.generate())

    // Assert
    finder.results
        .test()
        .awaitValue()
        .assertValue(fakeArtists.map { it.asDomain() })
}

@Test
fun `load next page fetch results for the same search, concatenate those results with current results and increment current page`() = runBlockingTest {
    // Arrange
    val fakeSearchText = RandomStringGenerator.generate()

    val fakeInitialArtists = (0..1).map { RandomMBArtistGenerator.generate() }
    val fakeInitialResult = RandomMBSearchArtistResultGenerator.generate(count = Int.MAX_VALUE, artists = fakeInitialArtists)

    every { mockArtistAPI.searchAsync(any(), any(), any()) } returns fakeInitialResult.toDeferred()

    finder.search(fakeSearchText)

    val fakeNextPageFakeArtists = (0..1).map { RandomMBArtistGenerator.generate() }
    val fakeNextPageResult = RandomMBSearchArtistResultGenerator.generate(count = Int.MAX_VALUE, artists = fakeNextPageFakeArtists)

    every { mockArtistAPI.searchAsync(any(), any(), any()) } returns fakeNextPageResult.toDeferred()

    val allArtists = mutableListOf<MBArtist>()
    allArtists.addAll(fakeInitialArtists)
    allArtists.addAll(fakeNextPageFakeArtists)

    val currentPage = finder.currentPage

    // Act
    finder.loadNextPage()

    // Assert
    finder.results
        .test()
        .awaitValue()
        .assertValue(allArtists.map { it.asDomain() })

    verify(exactly = 2) { mockArtistAPI.searchAsync(fakeSearchText, any(), any()) }

    assertEquals(currentPage + 1, finder.currentPage)
}

@Test
fun `has no more pages when results size is equal to count`() = runBlockingTest {
    // Arrange
    val fakeArtists = (0..10).map { RandomMBArtistGenerator.generate() }
    val fakeResult = MBSearchArtistResult(count = fakeArtists.size, artists = fakeArtists)

    every { mockArtistAPI.searchAsync(any(), any(), any()) } returns fakeResult.toDeferred()

    // Act
    finder.search(RandomStringGenerator.generate())

    // Assert
    finder.results
        .test()
        .awaitValue()

    assertFalse(finder.hasMorePages)
}

@Test
fun `do not load next page if there is none`() {
    // Arrange
    val observer = finder.results
        .test()

    val fakeArtists = (0..10).map { RandomMBArtistGenerator.generate() }
    val fakeResult = MBSearchArtistResult(count = fakeArtists.size, artists = fakeArtists)

    every { mockArtistAPI.searchAsync(any(), any(), any()) } returns fakeResult.toDeferred()

    runBlockingTest {
        finder.search(RandomStringGenerator.generate())
    }

    // Act
    runBlockingTest {
        finder.loadNextPage()
    }

    // Assert
    observer.awaitValue()

    verify(exactly = 1) { mockArtistAPI.searchAsync(any(), any(), any()) }
}

@Test
fun `reset pagination when new search text is set`() = runBlockingTest {
    // Arrange
    val fakeFirstArtists = (0..10).map { RandomMBArtistGenerator.generate() }
    val fakeFirstResult = RandomMBSearchArtistResultGenerator.generate(count = Int.MAX_VALUE, artists = fakeFirstArtists)
    every { mockArtistAPI.searchAsync(any(), any(), any()) } returns fakeFirstResult.toDeferred()

    finder.search(RandomStringGenerator.generate())

    val fakeSecondArtists = (0..10).map { RandomMBArtistGenerator.generate() }
    val fakeSecondResult = RandomMBSearchArtistResultGenerator.generate(count = Int.MAX_VALUE, artists = fakeSecondArtists)
    every { mockArtistAPI.searchAsync(any(), any(), any()) } returns fakeSecondResult.toDeferred()

    finder.loadNextPage()

    val fakeThirdArtists = (0..10).map { RandomMBArtistGenerator.generate() }
    val allArtists = mutableListOf<MBArtist>()
    allArtists.addAll(fakeFirstArtists)
    allArtists.addAll(fakeSecondArtists)
    allArtists.addAll(fakeThirdArtists)

    val fakeThirdResult = MBSearchArtistResult(count = allArtists.size, artists = fakeThirdArtists)
    every { mockArtistAPI.searchAsync(any(), any(), any()) } returns fakeThirdResult.toDeferred()

    finder.loadNextPage()

    // Act
    finder.search(RandomStringGenerator.generate())

    // Assert
    assertEquals(0, finder.currentPage)
    assertTrue(finder.hasMorePages)
}*/
}

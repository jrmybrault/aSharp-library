package com.jbr.asharplibrary.searchartist.ui

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.createRule
import com.jbr.asharplibrary.random.RandomDisplayableFoundArtistItemGenerator
import com.jbr.asharplibrary.random.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.jbr.asharplibrary.random.itemAtPosition
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module

@RunWith(AndroidJUnit4ClassRunner::class)
class SearchArtistFragmentTest {

    private val fakeIsSearching = MutableLiveData<Boolean>()
    private val fakeDisplayableResults = MutableLiveData<List<DisplayableFoundArtistItem>>()
    private val fakeShouldDisplayPreviousSearches = MutableLiveData<Boolean>()
    private val fakeDisplayablePreviousSearches = MutableLiveData<List<DisplayableFoundArtistItem>>()

    private var mockViewModel = mockk<SearchArtistViewModel>(relaxed = true) {
        every { isSearching } returns fakeIsSearching
        every { displayableResults } returns fakeDisplayableResults
        every { shouldDisplayPreviousSearches } returns fakeShouldDisplayPreviousSearches
        every { displayablePreviousSearches } returns fakeDisplayablePreviousSearches
    }

    private val fragment = SearchArtistFragment()

    @get:Rule
    val fragmentRule = createRule(fragment, module {
        single(override = true) {
            mockViewModel
        }
    })

    //region - Setup & Teardown

    @After
    fun tearDown() {
        fakeIsSearching.postValue(false)
        fakeDisplayableResults.postValue(emptyList())
        fakeShouldDisplayPreviousSearches.postValue(false)
        fakeDisplayablePreviousSearches.postValue(emptyList())
    }

    //endregion

    //region - Data observing

    @Test
    fun showResults() {
        // Act
        fakeShouldDisplayPreviousSearches.postValue(true)

        // Assert
        onView(withId(PREVIOUS_SEARCHES_RECYCLER_VIEW_ID))
            .check(matches(isDisplayed()))
        onView(withId(PREVIOUS_SEARCHES_TEXT_VIEW_ID))
            .check(matches(isDisplayed()))

        onView(withId(RESULTS_RECYCLER_VIEW_ID))
            .check(matches(not(isDisplayed())))
        onView(withId(RESULTS_TEXT_VIEW_ID))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun hidePreviousSearches() {
        // Act
        fakeShouldDisplayPreviousSearches.postValue(false)

        // Assert
        onView(withId(PREVIOUS_SEARCHES_RECYCLER_VIEW_ID))
            .check(matches(not(isDisplayed())))
        onView(withId(PREVIOUS_SEARCHES_TEXT_VIEW_ID))
            .check(matches(not(isDisplayed())))

        onView(withId(RESULTS_RECYCLER_VIEW_ID))
            .check(matches(isDisplayed()))
        onView(withId(RESULTS_TEXT_VIEW_ID))
            .check(matches(isDisplayed()))
    }

    @Test
    fun resultsItemsMustMatchData() {
        // Arrange
        val fakeDisplayables = (0..10).map { RandomDisplayableFoundArtistItemGenerator.generate() }

        // Act
        fakeDisplayableResults.postValue(fakeDisplayables)

        // Assert
        onView(withId(RESULTS_RECYCLER_VIEW_ID)).check(withItemCount(fakeDisplayables.size))
        onView(withId(RESULTS_RECYCLER_VIEW_ID))
            .check(matches(itemAtPosition(1, hasDescendant(withText(fakeDisplayables[1].name)))))
    }

    @Test
    fun previousSearchesItemsMustMatchData() {
        // Arrange
        val fakeDisplayables = (0..10).map { RandomDisplayableFoundArtistItemGenerator.generate() }

        fakeShouldDisplayPreviousSearches.postValue(true)

        // Act
        fakeDisplayablePreviousSearches.postValue(fakeDisplayables)

        // Assert
        onView(withId(PREVIOUS_SEARCHES_RECYCLER_VIEW_ID)).check(withItemCount(fakeDisplayables.size))
        onView(withId(PREVIOUS_SEARCHES_RECYCLER_VIEW_ID))
            .check(matches(itemAtPosition(1, hasDescendant(withText(fakeDisplayables[1].name)))))
    }

    @Test
    fun showProgressBar() {
        // Act
        fakeIsSearching.postValue(true)

        // Assert
        onView(withId(SEARCH_PROGRESS_BAR_ID))
            .check(matches(isDisplayed()))
    }

    @Test
    fun hideProgressBar() {
        // Act
        fakeIsSearching.postValue(false)

        // Assert
        onView(withId(SEARCH_PROGRESS_BAR_ID))
            .check(matches(not(isDisplayed())))
    }

    //endregion

    //region - User interaction

    @Test
    fun clickOnResultItemDelegateToViewModel() {
        // Act
        val itemPosition = 0
        val fakeDisplayables = (0..10).map { RandomDisplayableFoundArtistItemGenerator.generate() }

        fakeDisplayableResults.postValue(fakeDisplayables)

        // Act
        onView(withId(RESULTS_RECYCLER_VIEW_ID)).perform(actionOnItemAtPosition<FoundArtistViewHolder>(itemPosition, click()))

        // Assert
        verify { mockViewModel.handleSelectionOfArtist(eq(itemPosition)) }
    }

    @Test
    fun clickOnPreviousSearchItemDelegateToViewModel() {
        // Act
        val itemPosition = 0
        val fakeDisplayables = (0..10).map { RandomDisplayableFoundArtistItemGenerator.generate() }

        fakeShouldDisplayPreviousSearches.postValue(true)
        fakeDisplayablePreviousSearches.postValue(fakeDisplayables)

        // Act
        onView(withId(PREVIOUS_SEARCHES_RECYCLER_VIEW_ID)).perform(actionOnItemAtPosition<FoundArtistViewHolder>(itemPosition, click()))

        // Assert
        verify { mockViewModel.handleSelectionOfPreviousSearch(eq(itemPosition)) }
    }

    @Test
    fun swipePreviousSearchDelegateToViewModel() {
        // Act
        val itemPosition = 0
        val fakeDisplayables = (0..10).map { RandomDisplayableFoundArtistItemGenerator.generate() }

        fakeShouldDisplayPreviousSearches.postValue(true)
        fakeDisplayablePreviousSearches.postValue(fakeDisplayables)

        // Act
        onView(withId(PREVIOUS_SEARCHES_RECYCLER_VIEW_ID)).perform(actionOnItemAtPosition<FoundArtistViewHolder>(itemPosition, swipeRight()))

        // Assert
        verify { mockViewModel.handleSelectionOfPreviousSearch(eq(itemPosition)) }
    }

    @Test
    fun scrollingToLastResultsDelegateToViewModel() {
        // Act
        val fakeDisplayables = (0..10).map { RandomDisplayableFoundArtistItemGenerator.generate() }

        fakeDisplayableResults.postValue(fakeDisplayables)

        // Act
        onView(withId(RESULTS_RECYCLER_VIEW_ID)).perform(scrollToPosition<FoundArtistViewHolder>(fakeDisplayables.size - 1))

        // Assert
        verify { mockViewModel.handleReachingListEnd() }
    }

    //endregion

    private companion object {

        private const val SEARCH_PROGRESS_BAR_ID = R.id.searchProgressBar

        private const val RESULTS_RECYCLER_VIEW_ID = R.id.resultsRecyclerView
        private const val RESULTS_TEXT_VIEW_ID = R.id.resultTextView

        private const val PREVIOUS_SEARCHES_RECYCLER_VIEW_ID = R.id.previousSearchesRecyclerView
        private const val PREVIOUS_SEARCHES_TEXT_VIEW_ID = R.id.previousSearchesTextView
    }
}
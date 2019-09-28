package com.jbr.asharplibrary.experimentationlegacy

/*@RunWith(AndroidJUnit4::class)
class SearchArtistFragmentTest : BaseFragmentTest(R.id.searchArtistFragment) {

    private val displayPreviousSearches = MutableLiveData<Boolean>()

    private var mockViewModel = mockk<SearchArtistViewModel>(relaxed = true) {
        every { shouldDisplayPreviousSearches } returns displayPreviousSearches
    }

    override fun createTestModule(): Module {
        return module {
            single { mockViewModel }
        }
    }

    @Test
    fun showResults() {
        // Arrange
        displayPreviousSearches.postValue(true)

        // Assert
        onView(ViewMatchers.withId(R.id.previousSearchesRecyclerView))
            .check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.previousSearchesTextView))
            .check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.foundArtistsRecyclerView))
            .check(matches(not(isDisplayed())))
        onView(ViewMatchers.withId(R.id.resultTextView))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun hidePreviousSearches() {
        // Arrange
        displayPreviousSearches.postValue(false)

        // Assert
        onView(ViewMatchers.withId(R.id.previousSearchesRecyclerView))
            .check(matches(not(isDisplayed())))
        onView(ViewMatchers.withId(R.id.previousSearchesTextView))
            .check(matches(not(isDisplayed())))

        onView(ViewMatchers.withId(R.id.foundArtistsRecyclerView))
            .check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.resultTextView))
            .check(matches(isDisplayed()))
    }
}*/
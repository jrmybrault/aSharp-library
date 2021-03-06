package com.jbr.asharplibrary.searchartist.ui

/*@RunWith(AndroidJUnit4ClassRunner::class)
class FoundArtistsListAdapterTest {

    private val adapter = FoundArtistsListAdapter()

    private val recyclerView = RecyclerView(InstrumentationRegistry.getInstrumentation().targetContext)

    @Before
    fun setUp() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }
    }

    // FIXME: The following test does not work because the viewHolder's itemView is not properly mocked...
    // A simple solution could be to attach the click listener to the view holder itself instead of its itemView
    @Test
    fun testBindingViewHolderDelegatesToViewHolder() {
        // Arrange
        val fakeDisplayables = (0..3).map { RandomDisplayableFoundArtistItemGenerator.generate() }
        adapter.submitList(fakeDisplayables)

        val itemPosition = Random.nextInt(0, fakeDisplayables.size - 1)

        val displayableCaptor = ArgumentCaptor.forClass(DisplayableFoundArtistItem::class.java)

        val mockViewHolder = mockk<FoundArtistViewHolder>()

        // Act
        adapter.bindViewHolder(mockViewHolder, itemPosition)

        // Assert
        verify { mockViewHolder.bind(displayableCaptor.capture(), any()) }
        assertEquals(fakeDisplayables[itemPosition], displayableCaptor.value)
    }
}*/
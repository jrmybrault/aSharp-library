package com.jbr.asharplibrary.searchartist.ui

/*@RunWith(RobolectricTestRunner::class)
class FoundArtistsListAdapterTest {

    private val displayables = listOf(
        RandomDisplayableFoundArtistItemGenerator.generate(),
        RandomDisplayableFoundArtistItemGenerator.generate(),
        RandomDisplayableFoundArtistItemGenerator.generate()
    )

    private val adapter = FoundArtistsListAdapter(ApplicationProvider.getApplicationContext())

    @Before
    fun setUp() {
        adapter.submitList(displayables)
    }

    @Test
    fun `binding view holder delegates binding to view holder`() {
        // Arrange
        val mockViewHolder = mock<FoundArtistViewHolder>()

        // Act
        adapter.bindViewHolder(mockViewHolder, 0)

        // Assert
        verify(mockViewHolder).bind(eq(displayables[0]))
    }

    @Test
    fun `after binding, tap callback is called when view is clicked`() {
        // Arrange
        val mockViewHolder = mock<FoundArtistViewHolder>()
        val tapCallback = mock<Consumer<Int>>()

        adapter.onArtistTap = tapCallback

        val captor = ArgumentCaptor.forClass(View.OnClickListener::class.java)

        val bindingPosition = Random.nextInt(0, displayables.size - 1)

        // Act
        adapter.bindViewHolder(mockViewHolder, 0)

        // Assert
        verify(mockViewHolder.itemView).setOnClickListener(captor.capture())

        captor.value.onClick(mockViewHolder.itemView)
        verify(tapCallback).accept(eq(bindingPosition))
    }
}*/
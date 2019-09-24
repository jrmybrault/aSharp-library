package com.jbr.asharplibrary.searchartist.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.util.Consumer
import androidx.core.view.MenuItemCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.searchartist.usecase.SearchArtistNavigator
import com.jbr.asharplibrary.shared.ui.DEFAULT_TEXT_EDITION_THROTTLE_DELAY
import com.jbr.asharplibrary.shareddomain.ArtistIdentifier
import kotlinx.android.synthetic.main.fragment_search_artist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

class SearchArtistFragment : Fragment(), CoroutineScope, SearchArtistNavigator {

    //region - Properties

    private val viewModel: SearchArtistViewModel by viewModel()

    private val foundArtistsListAdapter: FoundArtistsListAdapter by lazyOf(FoundArtistsListAdapter())

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private var editedSearchText: String? = null

    //endregion

    //region - Functions

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.isSearching.observe(viewLifecycleOwner, Observer { searchProgressBar.isVisible = it })
        viewModel.displayableFoundArtists.observe(viewLifecycleOwner, Observer { foundArtistsListAdapter.artists = it })
        viewModel.searchResultText.observe(viewLifecycleOwner, Observer { resultTextView.text = it })

        viewModel.navigator = WeakReference(this)

        return inflater.inflate(R.layout.fragment_search_artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        foundArtistsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = foundArtistsListAdapter
        }

        foundArtistsListAdapter.onArtistSelected = Consumer { viewModel.handleSelectionOfArtist(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_artist, menu)

        setupSearchView(menu)
    }

    private fun setupSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = SearchView(activity)
        MenuItemCompat.setActionView(searchItem, searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                throttleSearch(newText)

                return true
            }
        })
    }

    private fun throttleSearch(text: String) {
        editedSearchText = text

        launch {
            delay(DEFAULT_TEXT_EDITION_THROTTLE_DELAY)

            if (text != editedSearchText) {
                return@launch
            }

            viewModel.searchText = text
        }
    }

    override fun goToArtistDetails(identifier: ArtistIdentifier) {
        findNavController().navigate(SearchArtistFragmentDirections.actionSearchArtistFragmentToArtistDetailsFragment(identifier))
    }

    //endregion
}
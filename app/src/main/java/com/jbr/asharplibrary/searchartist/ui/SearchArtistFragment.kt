package com.jbr.asharplibrary.searchartist.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_artist.*
import org.koin.android.viewmodel.ext.android.viewModel


class SearchArtistFragment : Fragment() {

    //region - Properties

    private val viewModel: SearchArtistViewModel by viewModel()

    private val foundArtistsListAdapter: FoundArtistsListAdapter by lazyOf(FoundArtistsListAdapter())

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

        return inflater.inflate(com.jbr.asharplibrary.R.layout.fragment_search_artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.jbr.asharplibrary.R.menu.menu_search_artist, menu)
        val searchItem = menu.findItem(com.jbr.asharplibrary.R.id.action_search)

        val searchView = SearchView(activity)
        MenuItemCompat.setActionView(searchItem, searchView)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchText = newText
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        foundArtistsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = foundArtistsListAdapter

            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
        }
    }

    //endregion
}
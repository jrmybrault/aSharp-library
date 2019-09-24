package com.jbr.asharplibrary.artistdetails.ui.discography

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.artistdetails.ui.ArtistDetailsViewModel
import com.jbr.utils.isDeviceTablet
import kotlinx.android.synthetic.main.fragment_artist_details_discography.*
import org.koin.android.viewmodel.ext.android.viewModel

class ArtistDetailsDiscographyFragment : Fragment() {


    //region - Properties

    private val viewModel: ArtistDetailsViewModel by viewModel()

    private val artistReleasesListAdapter: ArtistReleasesListAdapter by lazyOf(ArtistReleasesListAdapter())

    //endregion

    //region - Functions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.displayableArtistReleases.observe(viewLifecycleOwner, Observer { artistReleasesListAdapter.releases = it })

        return inflater.inflate(R.layout.fragment_artist_details_discography, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        releasesRecyclerView.apply {
            layoutManager = if (isDeviceTablet(resources)) {
                LinearLayoutManager(context)
            } else {
                GridLayoutManager(activity, 3)
            }
            adapter = artistReleasesListAdapter
        }
    }

    //endregion
}
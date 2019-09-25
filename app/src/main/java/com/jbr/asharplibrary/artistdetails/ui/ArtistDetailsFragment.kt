package com.jbr.asharplibrary.artistdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.shared.ui.ImageDownloader
import kotlinx.android.synthetic.main.fragment_artist_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ArtistDetailsFragment : Fragment() {

    //region - Properties

    private val args: ArtistDetailsFragmentArgs by navArgs()

    private val viewModel: ArtistDetailsViewModel by viewModel()

    private val imageDownloader: ImageDownloader by inject()

    //endregion

    //region - Functions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.loadArtist(args.artistIdentifier)

        viewModel.displayableArtistName.observe(viewLifecycleOwner, Observer {
            detailsCollapsingToolbarLayout.title = it
        })
        viewModel.randomReleaseCoverUri.observe(viewLifecycleOwner, Observer { coverUri ->
            if (coverUri != null) {
                imageDownloader.downloadImage(coverUri, detailsHeaderImageView, activity as AppCompatActivity, null)
            }
        })

        return inflater.inflate(R.layout.fragment_artist_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupCollapse()
    }

    private fun setupViewPager() {
        val adapter = ArtistDetailsPagerAdapter(fragmentManager!!, resources)

        detailsViewPager.adapter = adapter
        detailsTabLayout.setupWithViewPager(detailsViewPager)
    }

    private fun setupCollapse() {
        detailsCollapsingToolbarLayout.isTitleEnabled = true
    }

    //endregion
}
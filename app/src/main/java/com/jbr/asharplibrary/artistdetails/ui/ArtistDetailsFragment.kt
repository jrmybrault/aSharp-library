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
import com.jbr.asharplibrary.shared.ui.ReleaseCoverImageDownloader
import kotlinx.android.synthetic.main.fragment_artist_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ArtistDetailsFragment : Fragment() {

    //region - Properties

    private val args: ArtistDetailsFragmentArgs by navArgs()

    private val viewModel: ArtistDetailsViewModel by viewModel()

    private val mReleaseCoverImageDownloader: ReleaseCoverImageDownloader by inject()

    //endregion

    //region - Functions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.loadArtist(args.artistIdentifier)

        viewModel.displayableArtistName.observe(viewLifecycleOwner, Observer {
            detailsCollapsingToolbarLayout.title = it
        })
        viewModel.randomReleaseCoverUri.observe(viewLifecycleOwner, Observer { coverUri ->
            if (coverUri != null) {
                mReleaseCoverImageDownloader.downloadImage(coverUri, detailsHeaderImageView, activity as AppCompatActivity, null)
            }
        })

        return inflater.inflate(R.layout.fragment_artist_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        detailsViewPager.adapter = ArtistDetailsPagerAdapter(fragmentManager!!, resources)
        detailsTabLayout.setupWithViewPager(detailsViewPager)
    }

    //endregion
}
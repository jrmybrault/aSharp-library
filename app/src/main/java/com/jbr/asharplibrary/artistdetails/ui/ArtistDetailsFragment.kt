package com.jbr.asharplibrary.artistdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jbr.asharplibrary.R
import kotlinx.android.synthetic.main.fragment_artist_details.*

class ArtistDetailsFragment : Fragment() {

    //region - Functions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_artist_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = ArtistDetailsPagerAdapter(fragmentManager!!, resources)

        detailsViewPager.adapter = adapter
        detailsTabLayout.setupWithViewPager(detailsViewPager)
    }
    //endregion
}
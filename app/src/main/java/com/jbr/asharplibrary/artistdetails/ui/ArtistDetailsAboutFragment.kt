package com.jbr.asharplibrary.artistdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jbr.asharplibrary.R

class ArtistDetailsAboutFragment : Fragment() {

    //region - Functions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_artist_details_about, container, false)
    }

    //endregion
}
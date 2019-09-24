package com.jbr.asharplibrary.artistdetails.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.artistdetails.ui.ArtistDetailsViewModel
import kotlinx.android.synthetic.main.fragment_artist_details_about.*
import org.koin.android.viewmodel.ext.android.viewModel

class ArtistDetailsAboutFragment : Fragment() {

    //region - Properties

    private val viewModel: ArtistDetailsViewModel by viewModel()

    //endregion

    //region - Functions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.displayableArtistAbout.observe(viewLifecycleOwner, Observer {
            onDisplayableChanged(it)
        })

        return inflater.inflate(R.layout.fragment_artist_details_about, container, false)
    }

    private fun onDisplayableChanged(displayableArtistAbout: DisplayableArtistAbout) {
        detailsGenderTextView.isVisible = displayableArtistAbout.shouldDisplayGender
        detailsGenderTextView.text = displayableArtistAbout.genderText

        detailsLifeSpanBeginLayout.isVisible = displayableArtistAbout.shouldDisplayLifeSpanBegin
        detailsLifeSpanBeginTitleTextView.text = displayableArtistAbout.lifeSpanBeginningTitleText
        detailsLifeSpanBeginTextView.text = displayableArtistAbout.lifeSpanBeginningText

        detailsLifeSpanEndLayout.isVisible = displayableArtistAbout.shouldDisplayLifeSpanEnd
        detailsLifeSpanEndTitleTextView.text = displayableArtistAbout.lifeSpanEndTitleText
        detailsLifeSpanEndTextView.text = displayableArtistAbout.lifeSpanEndText

        detailsCountryTextView.text = displayableArtistAbout.countryText

        detailsIpiCodesLayout.isVisible = displayableArtistAbout.shouldDisplayIpiCodes
        detailsIpiCodesTextView.text = displayableArtistAbout.ipiCodesText
        detailsIsniCodesLayout.isVisible = displayableArtistAbout.shouldDisplayIsniCodes
        detailsIsniCodesTextView.text = displayableArtistAbout.isniCodesText

        detailsWikipediaExtractTextView.text = displayableArtistAbout.wikipediaExtractText
    }

    //endregion
}
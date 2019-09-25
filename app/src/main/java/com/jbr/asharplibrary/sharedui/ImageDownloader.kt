package com.jbr.asharplibrary.shared.ui

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.jbr.asharplibrary.R

data class ImageDownloadOptions(val placeholderImageId: Int, val errorImageId: Int)

interface ImageDownloader {

    fun downloadImage(
        uri: Uri,
        imageView: ImageView,
        context: Context,
        options: ImageDownloadOptions? = ImageDownloadOptions(
            R.drawable.loading_animation,
            R.drawable.ic_broken_image
        )
    )
}

package com.jbr.coredomain.coverdownload

import android.content.Context
import android.net.Uri
import android.widget.ImageView

data class ImageDownloadOptions(val placeholderImageId: Int, val errorImageId: Int)

interface ReleaseCoverImageDownloader {

    fun downloadImage(
        uri: Uri,
        imageView: ImageView,
        context: Context,
        options: ImageDownloadOptions?
    )
}

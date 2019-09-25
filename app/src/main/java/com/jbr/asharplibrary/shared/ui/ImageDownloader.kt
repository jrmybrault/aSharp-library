package com.jbr.asharplibrary.shared.ui

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
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

class GlideImageDownloader : ImageDownloader {

    override fun downloadImage(uri: Uri, imageView: ImageView, context: Context, options: ImageDownloadOptions?) {
        Glide.with(context)
            .load(uri)
            .placeholder(options?.placeholderImageId ?: android.R.color.transparent)
            .error(options?.errorImageId ?: android.R.color.transparent)
            .transition(withCrossFade())
            .into(imageView)
    }
}
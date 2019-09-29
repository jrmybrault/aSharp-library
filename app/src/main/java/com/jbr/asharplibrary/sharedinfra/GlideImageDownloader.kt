package com.jbr.asharplibrary.sharedinfra

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jbr.asharplibrary.shared.ui.ImageDownloadOptions
import com.jbr.asharplibrary.shared.ui.ReleaseCoverImageDownloader

class GlideImageDownloader : ReleaseCoverImageDownloader {

    override fun downloadImage(uri: Uri, imageView: ImageView, context: Context, options: ImageDownloadOptions?) {
        Glide.with(context)
            .load(uri)
            .placeholder(options?.placeholderImageId ?: android.R.color.transparent)
            .error(options?.errorImageId ?: android.R.color.transparent)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}
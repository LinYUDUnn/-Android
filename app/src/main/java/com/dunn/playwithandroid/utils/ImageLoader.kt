package com.dunn.playwithandroid.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * Created by DunnLin on 2019/7/27.
 */
class ImageLoader {

    companion object {
        fun load(context: Context, url: String, iv: ImageView) {
            Glide.with(context)
                .load(url)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv)
        }
    }
}
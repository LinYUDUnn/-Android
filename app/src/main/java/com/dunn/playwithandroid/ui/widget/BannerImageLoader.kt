package com.dunn.playwithandroid.ui.widget

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader

/**
 * Created by DunnLin on 2019/7/27.
 */
class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        com.dunn.playwithandroid.utils.ImageLoader.load(context, path as String, imageView)
    }
}
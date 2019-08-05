package com.dunn.playwithandroid.utils

import android.content.Context

/**
 * Created by DunnLin on 2019/7/27.
 */
class CommonUtil {

    companion object {
        fun dp2px(context: Context, dipValue: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }
    }
}
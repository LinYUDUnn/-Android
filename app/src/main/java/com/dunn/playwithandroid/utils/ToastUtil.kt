package com.dunn.playwithandroid.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by DunnLin on 2019/7/25.
 */
class ToastUtil {

    companion object {
        fun show(context: Context?, content: String?) {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show()
        }

        fun show(context: Context, content: Int) {
            Toast.makeText(context, context.getString(content), Toast.LENGTH_SHORT).show()
        }
    }

}
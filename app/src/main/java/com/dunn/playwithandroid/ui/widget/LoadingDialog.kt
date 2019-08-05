package com.dunn.playwithandroid.ui.widget

import com.dunn.playwithandroid.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * Created by DunnLin on 2019/7/25.
 */
class LoadingDialog : BaseNiceDialog() {
    override fun intLayoutId(): Int {
        return R.layout.dialog_loading_layout
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        setDimAmount(0f)
        setOutCancel(false)
    }

    override fun initTheme(): Int {
        return R.style.MyDialog

    }

    companion object {
        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }
}
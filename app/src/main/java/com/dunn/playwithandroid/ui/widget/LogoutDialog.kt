package com.dunn.playwithandroid.ui.widget

import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.dunn.playwithandroid.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.NiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * Created by DunnLin on 2019/7/26.
 */
class LogoutDialog : NiceDialog() {

    companion object {
        fun show(fragmentManager: FragmentManager, listener: OnLogoutListener) {
            LogoutDialog().setOnLogoutListener(listener).show(fragmentManager)
        }
    }

    override fun convertView(holder: ViewHolder, dialog: BaseNiceDialog) {
        setMargin(60)
        with(holder) {
            getView<TextView>(R.id.logoutCancelBtn).setOnClickListener {
                dialog.dismiss()
            }
            getView<TextView>(R.id.logoutOkBtn).setOnClickListener {
                listener.logout()
                dialog.dismiss()
            }
        }
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_logout_layout
    }

    private lateinit var listener: OnLogoutListener

    fun setOnLogoutListener(listener: OnLogoutListener): LogoutDialog {
        this.listener = listener
        return this
    }

    interface OnLogoutListener {
        fun logout()
    }
}
package com.dunn.playwithandroid.ui.main

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException

/**
 * Created by DunnLin on 2019/7/24.
 */
interface MainContract {

    interface View : BaseView {
        fun onLogoutSuccess(data: String)
        fun onLogoutError(e: ResponseException)
    }

    interface Presenter {
        fun logout()
    }

}
package com.dunn.playwithandroid.ui.register

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.RegisterBean

/**
 * Created by DunnLin on 2019/7/26.
 */
interface RegisterContract {

    interface View : BaseView {
        fun onRegisterSuccess(data: RegisterBean)
        fun onRegisterError(e: ResponseException)
    }

    interface Presenter {
        fun register(username: String, password: String, repassword: String)
    }

}
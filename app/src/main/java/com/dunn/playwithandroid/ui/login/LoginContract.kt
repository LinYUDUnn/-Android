package com.dunn.playwithandroid.ui.login

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.LoginBean

/**
 * Created by DunnLin on 2019/7/26.
 */
interface LoginContract {

    interface View : BaseView {
        fun onLoginSuccess(data: LoginBean)
        fun onLoginError(e: ResponseException)
    }

    interface Presenter {
        fun login(userName: String, password: String)
    }

}
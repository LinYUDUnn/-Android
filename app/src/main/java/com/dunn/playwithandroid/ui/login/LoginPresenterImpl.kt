package com.dunn.playwithandroid.ui.login

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.LoadingObserver
import com.dunn.playwithandroid.bean.LoginBean

/**
 * Created by DunnLin on 2019/7/26.
 */
class LoginPresenterImpl(view: LoginContract.View) : BasePresenter<LoginContract.View>(view), LoginContract.Presenter {
    override fun login(userName: String, password: String) {
        val params = hashMapOf<String, String>()
        params["username"] = userName
        params["password"] = password
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).login(params),
            object : LoadingObserver<LoginBean>(context) {
                override fun onSuccess(data: LoginBean) {
                    view.onLoginSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onLoginError(e)
                }

            })
    }
}
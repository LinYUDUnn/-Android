package com.dunn.playwithandroid.ui.register

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.LoadingObserver
import com.dunn.playwithandroid.bean.RegisterBean

/**
 * Created by DunnLin on 2019/7/26.
 */
class RegisterPresenterImpl(view: RegisterContract.View) : BasePresenter<RegisterContract.View>(view),
    RegisterContract.Presenter {
    override fun register(username: String, password: String, repassword: String) {
        val params = hashMapOf<String, String>()
        params["username"] = username
        params["password"] = password
        params["repassword"] = repassword
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).register(params),
            object : LoadingObserver<RegisterBean>(context) {
                override fun onSuccess(data: RegisterBean) {
                    view.onRegisterSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onRegisterError(e)
                }

            })
    }
}
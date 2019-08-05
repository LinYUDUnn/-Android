package com.dunn.playwithandroid.ui.main

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.LoadingObserver

/**
 * Created by DunnLin on 2019/7/24.
 */
class MainPresenterImpl(view: MainContract.View) : BasePresenter<MainContract.View>(view), MainContract.Presenter {
    override fun logout() {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).logout(),
            object : LoadingObserver<String>(context) {
                override fun onSuccess(data: String) {
                    view.onLogoutSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onLogoutError(e)
                }

            })
    }
}
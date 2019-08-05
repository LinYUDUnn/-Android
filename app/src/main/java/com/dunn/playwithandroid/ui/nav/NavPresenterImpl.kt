package com.dunn.playwithandroid.ui.nav

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.bean.navi.NaviBean

/**
 * Created by DunnLin on 2019/7/27.
 */
class NavPresenterImpl(view: NavContract.View) : BasePresenter<NavContract.View>(view), NavContract.Presenter {

    override fun nav() {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).nav(),
            object : BaseObserver<List<NaviBean>>() {
                override fun onSuccess(data: List<NaviBean>) {
                    view.onNavSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onNavError(e)
                }

            })
    }
}
package com.dunn.playwithandroid.ui.website

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.bean.FriendBean

/**
 * Created by DunnLin on 2019/7/29.
 */
class HotWebsitePresenterImpl(view: HotWebsiteContract.View) : BasePresenter<HotWebsiteContract.View>(view),
    HotWebsiteContract.Presenter {
    override fun getFriendData() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).friend(),
            object : BaseObserver<List<FriendBean>>() {
                override fun onSuccess(data: List<FriendBean>) {
                    view.onFriedSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onFriendError(e)
                }
            })
    }
}
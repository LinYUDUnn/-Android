package com.dunn.playwithandroid.ui.tree

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.bean.tree.TreeBean

/**
 * Created by DunnLin on 2019/7/27.
 */
class TreePresenterImpl(view: TreeContract.View) : BasePresenter<TreeContract.View>(view), TreeContract.Presenter {

    override fun getTree() {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).tree(),
            object : BaseObserver<List<TreeBean>>() {
                override fun onSuccess(data: List<TreeBean>) {
                    view.onTreeSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onTreeError(e)
                }

            })
    }
}
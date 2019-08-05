package com.dunn.playwithandroid.ui.tree.treeDetail

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.base.net.observer.LoadingObserver
import com.dunn.playwithandroid.bean.treeDetail.TreeDetailBean

/**
 * Created by DunnLin on 2019/7/27.
 */
class TreeDetailPresenterImpl(view: TreeDetailContract.View) : BasePresenter<TreeDetailContract.View>(view),
    TreeDetailContract.Presenter {

    override fun collect(id: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id),
            object : LoadingObserver<String>(context) {
                override fun onSuccess(data: String) {
                    view.onCollectSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onCollectError(e)
                }
            })
    }

    override fun uncollect(id: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id),
            object : LoadingObserver<String>(context) {
                override fun onSuccess(data: String) {
                    view.onUncollectSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onUncollectError(e)
                }
            })
    }

    override fun getTreeDetail(pageNum: Int, cid: Int) {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).treeDetail(pageNum, cid),
            object : BaseObserver<TreeDetailBean>() {
                override fun onSuccess(data: TreeDetailBean) {
                    view.onTreeDetailSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onTreeDetailError(e)
                }

            })
    }
}
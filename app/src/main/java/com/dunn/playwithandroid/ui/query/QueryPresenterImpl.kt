package com.dunn.playwithandroid.ui.query

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.base.net.observer.LoadingObserver
import com.dunn.playwithandroid.bean.HotKeyBean
import com.dunn.playwithandroid.bean.query.QueryBean

/**
 * Created by DunnLin on 2019/7/29.
 */
class QueryPresenterImpl(view: QueryContract.View) : BasePresenter<QueryContract.View>(view), QueryContract.Presenter {

    override fun query(pageNum: Int, k: String, showLoading: Boolean) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).query(pageNum, k),
            object : LoadingObserver<QueryBean>(context, showLoading) {
                override fun onSuccess(data: QueryBean) {
                    view.onQuerySuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onQueryError(e)
                }
            })
    }

    override fun getHotKey() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).hotKey(),
            object : BaseObserver<List<HotKeyBean>>() {
                override fun onSuccess(data: List<HotKeyBean>) {
                    view.onHotKeySuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onHotKeyError(e)
                }
            })
    }

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
            object : LoadingObserver<String>(context, false, true) {
                override fun onSuccess(data: String) {
                    view.onUncollectSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onUncollectError(e)
                }
            })
    }
}
package com.dunn.playwithandroid.ui.collection

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.bean.article.ArticleBean

/**
 * Created by DunnLin on 2019/7/27.
 */
class MyCollectPrsenterImpl(view: MyCollectContract.View) : BasePresenter<MyCollectContract.View>(view),
    MyCollectContract.Presenter {

    override fun getCollectionList(pageNum: Int) {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).collectArticleList(pageNum),
            object : BaseObserver<ArticleBean>() {
                override fun onSuccess(data: ArticleBean) {
                    view.onCollectionListSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onCollectionListError(e)
                }

            })
    }

    override fun cancelCollection(id: Int, originId: Int) {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).cancelMyCollection(id, originId),
            object : BaseObserver<String>() {
                override fun onSuccess(data: String) {
                    view.onCancelCollectionSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onCancelCollectionError(e)
                }

            })
    }
}
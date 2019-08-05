package com.dunn.playwithandroid.ui.home

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.base.net.observer.LoadingObserver
import com.dunn.playwithandroid.bean.BannerBean
import com.dunn.playwithandroid.bean.article.ArticleBean

/**
 * Created by DunnLin on 2019/7/27.
 */
class HomePresenterImpl(view: HomeContract.View) : BasePresenter<HomeContract.View>(view), HomeContract.Presneter {
    override fun collect(id: Int) {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id),
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
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id),
            object : LoadingObserver<String>(context) {
                override fun onSuccess(data: String) {
                    view.onUncollectSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onUncollectError(e)
                }

            })
    }

    override fun getBannerData() {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).banner(),
            object : BaseObserver<List<BannerBean>>() {
                override fun onSuccess(data: List<BannerBean>) {
                    view.onBannerSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onBannerError(e)
                }

            })
    }

    override fun getArticleList(pageNum: Int) {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).articleList(pageNum),
            object : BaseObserver<ArticleBean>() {
                override fun onSuccess(data: ArticleBean) {
                    view.onArticleListSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onArticleListError(e)
                }

            })
    }
}
package com.dunn.playwithandroid.ui.home

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.BannerBean
import com.dunn.playwithandroid.bean.article.ArticleBean
import com.dunn.playwithandroid.ui.collection.CollectContract

/**
 * Created by DunnLin on 2019/7/27.
 */
interface HomeContract {

    interface View : BaseView, CollectContract.View {

        fun onBannerSuccess(data: List<BannerBean>)
        fun onBannerError(e: ResponseException)

        fun onArticleListSuccess(data: ArticleBean)
        fun onArticleListError(e: ResponseException)
    }

    interface Presneter : CollectContract.Presenter {
        fun getBannerData()
        fun getArticleList(pageNum: Int)
    }

}
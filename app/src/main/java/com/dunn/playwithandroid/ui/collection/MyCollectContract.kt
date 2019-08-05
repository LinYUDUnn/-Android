package com.dunn.playwithandroid.ui.collection

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.article.ArticleBean

/**
 * Created by DunnLin on 2019/7/27.
 */
interface MyCollectContract {

    interface View : BaseView {
        fun onCollectionListSuccess(data: ArticleBean)
        fun onCollectionListError(e: ResponseException)

        fun onCancelCollectionSuccess(data: String)
        fun onCancelCollectionError(e: ResponseException)
    }

    interface Presenter {
        fun getCollectionList(pageNum: Int)

        fun cancelCollection(id: Int, originId: Int)
    }

}
package com.dunn.playwithandroid.ui.query

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.HotKeyBean
import com.dunn.playwithandroid.bean.query.QueryBean
import com.dunn.playwithandroid.ui.collection.CollectContract

/**
 * Created by DunnLin on 2019/7/29.
 */
class QueryContract {

    interface View : BaseView, CollectContract.View {
        fun onQuerySuccess(data: QueryBean)
        fun onQueryError(e: ResponseException)

        fun onHotKeySuccess(data: List<HotKeyBean>)
        fun onHotKeyError(e: ResponseException)
    }

    interface Presenter : CollectContract.Presenter {
        fun query(pageNum: Int, k: String, showLoading: Boolean)

        fun getHotKey()
    }

}
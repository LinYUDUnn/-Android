package com.dunn.playwithandroid.ui.tree.treeDetail

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.treeDetail.TreeDetailBean
import com.dunn.playwithandroid.ui.collection.CollectContract

/**
 * Created by DunnLin on 2019/7/27.
 */
interface TreeDetailContract {

    interface View : BaseView, CollectContract.View {
        fun onTreeDetailSuccess(data: TreeDetailBean)
        fun onTreeDetailError(e: ResponseException)
    }

    interface Presenter : CollectContract.Presenter {
        fun getTreeDetail(pageNum: Int, cid: Int)
    }

}
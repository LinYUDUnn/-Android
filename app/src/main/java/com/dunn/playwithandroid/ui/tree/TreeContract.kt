package com.dunn.playwithandroid.ui.tree

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.tree.TreeBean

/**
 * Created by DunnLin on 2019/7/27.
 */
interface TreeContract {

    interface View : BaseView {
        fun onTreeSuccess(data: List<TreeBean>)
        fun onTreeError(e: ResponseException)
    }

    interface Presenter {
        fun getTree()
    }

}
package com.dunn.playwithandroid.ui.collection

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException

/**
 * Created by DunnLin on 2019/7/27.
 */
interface CollectContract {

    interface View : BaseView {
        fun onCollectSuccess(data: String)
        fun onCollectError(e: ResponseException)

        fun onUncollectSuccess(data: String)
        fun onUncollectError(e: ResponseException)
    }

    interface Presenter {
        fun collect(id: Int)

        fun uncollect(id: Int)
    }
}
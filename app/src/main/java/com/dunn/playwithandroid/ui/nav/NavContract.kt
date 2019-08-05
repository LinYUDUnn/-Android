package com.dunn.playwithandroid.ui.nav

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.navi.NaviBean

/**
 * Created by DunnLin on 2019/7/27.
 */
interface NavContract {

    interface View : BaseView {
        fun onNavSuccess(data: List<NaviBean>)
        fun onNavError(e: ResponseException)
    }

    interface Presenter {
        fun nav()
    }

}
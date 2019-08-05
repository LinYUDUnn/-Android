package com.dunn.playwithandroid.ui.website

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.FriendBean

/**
 * Created by DunnLin on 2019/7/29.
 */
interface HotWebsiteContract {

    interface View : BaseView {
        fun onFriedSuccess(data: List<FriendBean>)
        fun onFriendError(e: ResponseException)
    }

    interface Presenter {
        fun getFriendData()
    }

}
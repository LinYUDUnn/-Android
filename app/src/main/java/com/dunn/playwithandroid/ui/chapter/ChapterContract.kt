package com.dunn.playwithandroid.ui.chapter

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.ChapterBean

/**
 * Created by DunnLin on 2019/7/27.
 */
interface ChapterContract {

    interface View : BaseView {
        fun onChapterSuccess(data: List<ChapterBean>)
        fun onChapterError(e: ResponseException)
    }

    interface Presenter {
        fun getChapter()
    }

}
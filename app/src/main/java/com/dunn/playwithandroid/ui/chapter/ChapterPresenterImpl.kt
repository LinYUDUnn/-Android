package com.dunn.playwithandroid.ui.chapter

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.bean.ChapterBean

/**
 * Created by DunnLin on 2019/7/27.
 */
class ChapterPresenterImpl(view: ChapterContract.View) : BasePresenter<ChapterContract.View>(view),
    ChapterContract.Presenter {

    override fun getChapter() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).chapter(),
            object : BaseObserver<List<ChapterBean>>() {
                override fun onSuccess(data: List<ChapterBean>) {
                    view.onChapterSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onChapterError(e)
                }
            })
    }
}
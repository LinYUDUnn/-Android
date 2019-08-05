package com.dunn.playwithandroid.ui.chapter.chapterDetail

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.base.net.observer.LoadingObserver
import com.dunn.playwithandroid.bean.chapter.ChapterArticleBean

/**
 * Created by DunnLin on 2019/7/27.
 */
class ChapterDetailPresenterImpl (view: ChapterDetailContract.View) : BasePresenter<ChapterDetailContract.View>(view), ChapterDetailContract.Presenter {
    override fun uncollect(id: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id),
            object : LoadingObserver<String>(context) {
                override fun onSuccess(data: String) {
                    view.onUncollectSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onUncollectError(e)
                }
            })
    }

    override fun collect(id: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id),
            object : LoadingObserver<String>(context) {
                override fun onSuccess(data: String) {
                    view.onCollectSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onCollectError(e)
                }
            })
    }

    override fun getChapterArticleList(chapterId: Int, pageNum: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).chapterArticleList(chapterId, pageNum),
            object : BaseObserver<ChapterArticleBean>() {
                override fun onSuccess(data: ChapterArticleBean) {
                    view.onChapterArticleListSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onChapterArticleListError(e)
                }
            })
    }

    override fun queryChapterArticle(chapterId: Int, pageNum: Int, k: String) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).queryChapterArticle(chapterId, pageNum, k),
            object : BaseObserver<ChapterArticleBean>() {
                override fun onSuccess(data: ChapterArticleBean) {
                    view.onQueryChapterArticleListSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onQueryChapterArticleListError(e)
                }
            })
    }
}
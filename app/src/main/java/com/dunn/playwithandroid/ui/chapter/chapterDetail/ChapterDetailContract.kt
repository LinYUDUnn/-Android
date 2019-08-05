package com.dunn.playwithandroid.ui.chapter.chapterDetail

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.chapter.ChapterArticleBean
import com.dunn.playwithandroid.ui.collection.CollectContract

/**
 * Created by DunnLin on 2019/7/27.
 */
interface ChapterDetailContract {

    interface View : BaseView, CollectContract.View {
        fun onChapterArticleListSuccess(data: ChapterArticleBean)
        fun onChapterArticleListError(e: ResponseException)

        fun onQueryChapterArticleListSuccess(data: ChapterArticleBean)
        fun onQueryChapterArticleListError(e: ResponseException)
    }

    interface Presenter : CollectContract.Presenter {
        fun getChapterArticleList(chapterId: Int, pageNum: Int)

        fun queryChapterArticle(chapterId: Int, pageNum: Int, k: String)
    }

}
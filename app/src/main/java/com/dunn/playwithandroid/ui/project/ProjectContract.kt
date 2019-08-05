package com.dunn.playwithandroid.ui.project

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.ProjectCategoryBean

/**
 * Created by DunnLin on 2019/7/27.
 */
interface ProjectContract {

    interface View : BaseView {
        fun onProjectCategorySuccess(data: List<ProjectCategoryBean>)
        fun onProjectCategoryError(e: ResponseException)
    }

    interface Presenter {
        fun getProjectCategory()
    }
}